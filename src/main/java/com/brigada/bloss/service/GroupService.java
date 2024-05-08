package com.brigada.bloss.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GroupService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final IdentityServiceImpl identityService = (IdentityServiceImpl) delegateExecution.getProcessEngineServices().getIdentityService();

        if (identityService.createGroupQuery().groupId("guests").count() == 0) {
            Group guestsGroup = identityService.newGroup("guests");
            guestsGroup.setName("Guests");
            guestsGroup.setType("WORKFLOW");
            identityService.saveGroup(guestsGroup);
        }

        if (identityService.createGroupQuery().groupId("authorized").count() == 0) {
            Group authorizedGroup = identityService.newGroup("authorized");
            authorizedGroup.setName("Authorized");
            authorizedGroup.setType("WORKFLOW");
            identityService.saveGroup(authorizedGroup);
        }

        if (identityService.createGroupQuery().groupId("moderators").count() == 0) {
            Group moderatorGroup = identityService.newGroup("moderators");
            moderatorGroup.setName("Moderators");
            moderatorGroup.setType("WORKFLOW");
            identityService.saveGroup(moderatorGroup);
        }

        if (identityService.createUserQuery().userId("guest").count() == 0) {
            User guest = identityService.newUser("guest");
            guest.setFirstName("Guest");
            guest.setLastName("Guest");
            guest.setPassword("guest");
            guest.setEmail("guest@camunda.org");
            identityService.saveUser(guest, true);
            identityService.createMembership("guest", "guests");
        }

    }
}
