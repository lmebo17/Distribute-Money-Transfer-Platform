package com.azry.dmtp.validationserver.steps;

import com.azry.dmtp.model.ParticipantModel;
import com.azry.dmtp.model.TransferModel;
import com.azry.dmtp.model.TransferStatusInfo;
import com.azry.dmtp.model.TransferStatusModel;
import com.azry.dmtp.model.TransferStatusUpdateEvent;
import com.azry.dmtp.model.TransferTypeModel;
import com.azry.dmtp.validationserver.SpringIntegrationTest;
import com.azry.dmtp.validationserver.model.BlacklistedUser;
import com.azry.dmtp.validationserver.repository.BlacklistedUsersRepository;
import com.azry.dmtp.validationserver.service.ValidationServerConsumerService;
import com.azry.dmtp.validationserver.world.CucumberWorld;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
public class ValidationSteps extends SpringIntegrationTest {

    private final CucumberWorld world;

    private final ValidationServerConsumerService validationServerConsumerService;

    private final BlacklistedUsersRepository blacklistedUsersRepository;

    @Given("TransferModel is created with {string}, {string}, {string}, {string}, {string}, {string}, and {string}")
    public void transferModelIsCreatedWith(String transferType, String mts, String currency, String country, String amount, String senderPersonalNumber, String receiverPersonalNumber) {
        TransferModel transferModel = initTransferModel(transferType, mts, currency, amount);
        if ("SEND".equals(transferType)) {
            transferModel.setReceiveCountry(country);
        } else if ("RECEIVE".equals(transferType)) {
            transferModel.setSendCountry(country);
        }
        setSenderAndReceiverPersonalNumbers(transferModel, senderPersonalNumber, receiverPersonalNumber);
        initTransferStatusUpdateEvent(transferType, transferModel);
        world.setTransferStatusUpdateEvent(world.getTransferStatusUpdateEvent());
    }

    @Given("Sender with personal number {string} is {string}")
    public void checkBlackListedClientByPersonalNumber(String personalNumber, String isBlacklisted) {
        Optional<BlacklistedUser> user = blacklistedUsersRepository.findByPersonalNumber(Long.parseLong(personalNumber));
        assertEquals(user.isPresent(), Boolean.parseBoolean(isBlacklisted));
    }

    @When("Transfer is being validated")
    public void transferStatusUpdateEventIsBeingValidated() {
        TransferStatusUpdateEvent event = world.getTransferStatusUpdateEvent();
        validationServerConsumerService.validateTransferStatusUpdateEvent(event);
        TransferStatusModel status = event.getStatus().getStatus();
        world.setValid(status == TransferStatusModel.SEND_VALIDATED || status == TransferStatusModel.RECEIVE_VALIDATED);
    }

    @Then("Transfer is {string}")
    public void transferIsValid(String expectedValidity) {
        boolean expected = Boolean.parseBoolean(expectedValidity);
        assertEquals(expected, world.getValid());
    }

    private void setSenderAndReceiverPersonalNumbers(TransferModel transferModel, String senderPersonalNumber, String receiverPersonalNumber) {
        transferModel.setSender(ParticipantModel.builder()
                .personalNumber(senderPersonalNumber)
                .build());

        transferModel.setReceiver(ParticipantModel.builder()
                .personalNumber(receiverPersonalNumber)
                .build());
    }

    private void initTransferStatusUpdateEvent(String transferType, TransferModel transferModel) {
        TransferStatusUpdateEvent transferStatusUpdateEvent = TransferStatusUpdateEvent.builder()
                .eventSchemaVersion(1)
                .status(TransferStatusInfo.builder()
                        .status("SEND".equals(transferType) ? TransferStatusModel.SEND_VALIDATION_REQUESTED : TransferStatusModel.RECEIVE_VALIDATION_REQUESTED)
                        .statusTime(LocalDateTime.now())
                        .build())
                .transfer(transferModel)
                .build();
        world.setTransferStatusUpdateEvent(transferStatusUpdateEvent);
    }

    private TransferModel initTransferModel(String transferType, String mts, String currency, String amount) {
        return TransferModel.builder()
                .type(TransferTypeModel.valueOf(transferType))
                .mts(mts)
                .currency(currency)
                .amount(BigDecimal.valueOf(Float.parseFloat(amount)))
                .build();
    }
}
