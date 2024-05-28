package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.Invitation;
import com.frankie.workdev.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    boolean existsInvitationByReceiverUser(User receiverUser);

    List<Invitation> findAllByReceiverUser(User receiverUser);

    @Query("SELECT i FROM Invitation i WHERE (:email IS NULL OR i.receiverUser.email LIKE CONCAT('%', :email, '%')) ")
    Page<Invitation> searchInvitationByEmail(String email, Pageable pageable);
}
