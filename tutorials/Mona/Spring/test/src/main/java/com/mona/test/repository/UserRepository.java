//done with help from Emil

package com.mona.test.repository;

import com.mona.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
