package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.repository.StickerRepository;

import java.util.List;

/**
 * Created by Mikhail on 14.05.2017.
 */
@Service
public class StickerService {

    private static final int STICKER_COUNT = 1000;
    @Autowired
    private StickerRepository stickerRepository;

    public void createSticker(Sticker sticker) {
        sticker.setCount(STICKER_COUNT);
        stickerRepository.save(sticker);
    }

    public List<Sticker> getAllStickers() {
        return stickerRepository.findAll();
    }
}
