package org.sticker.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.sticker.pack.dto.StickerDto;
import org.sticker.pack.model.Image;
import org.sticker.pack.model.Pager;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.service.ImageService;
import org.sticker.pack.service.StickerService;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Mikhail on 14.05.2017.
 */
@Controller
public class StickerController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private StickerService stickerService;

    private static final int INITIAL_PAGE = 0;

    @PostMapping("/sticker")
    private String add(@RequestParam("stickerImage") MultipartFile stickerImage,
                       @RequestParam("stickerName") String stickerName,
                       @RequestParam("stickerPrice") float stickerPrice) {
        Image image = imageService.saveImage(stickerImage);
        Sticker sticker = new Sticker();
        sticker.setName(stickerName);
        sticker.setPrice(stickerPrice);
        sticker.setImage(image);
        stickerService.createSticker(sticker);
        return "redirect:/sticker";
    }

    @GetMapping("/sticker")
    private String listStickers(@RequestParam("page") Optional<Integer> page, Model model) {
                int pageValue = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Sticker> stickerList = stickerService.getAllStickers(new PageRequest(pageValue, 3));
        List<StickerDto> stickerDtos = stickerList.getContent().stream()
                .map(sticker -> {
                    StickerDto stickerDto = new StickerDto();
                    stickerDto.setName(sticker.getName());
                    stickerDto.setPrice(sticker.getPrice());
                    Path imagePath = imageService.loadImage(sticker.getImage().getHashName());
                    String imageUrl = MvcUriComponentsBuilder
                            .fromMethodName(ImageController.class, "serveFile", imagePath.getFileName().toString())
                            .build().toString();
                    stickerDto.setImageUrl(imageUrl);
                    return stickerDto;})
                .collect(Collectors.toList());
        Pager pager = new Pager(stickerList.getTotalPages(), stickerList.getNumber() + 1);
        model.addAttribute("stickerDtos", stickerDtos);
        model.addAttribute("stickerPage", stickerList);
        model.addAttribute("pager", pager);
        return "stickerList";
    }

    @DeleteMapping("/sticker")
    private void remove() {

    }
}
