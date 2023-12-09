package com.frankie.workdev.repository;

import com.frankie.workdev.entity.Company;
import com.frankie.workdev.entity.Invitation;
import com.frankie.workdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    boolean existsInvitationByReceiverUser(User receiverUser);

    List<Invitation> findAllByReceiverUser(User receiverUser);
}
