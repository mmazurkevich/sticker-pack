package org.sticker.pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sticker.pack.model.Image;

/**
 * Created by Mikhail on 14.05.2017.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, String>{
}
