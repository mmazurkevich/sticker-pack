package org.sticker.pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sticker.pack.model.User;

/**
 * Created by Mikhail on 18.06.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
