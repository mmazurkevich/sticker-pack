package org.sticker.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.sticker.pack.dto.StickerDto;
import org.sticker.pack.model.Image;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.service.ImageService;
import org.sticker.pack.service.StickerService;

import java.nio.file.Path;
import java.util.List;
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
    private String listStickers(Model model) {
        List<Sticker> stickerList = stickerService.getAllStickers();
        List<StickerDto> stickerDtos = stickerList.stream()
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
        model.addAttribute("stickerDtos", stickerDtos);
        return "stickerList";
    }

    @DeleteMapping("/sticker")
    private void remove() {

    }
}
