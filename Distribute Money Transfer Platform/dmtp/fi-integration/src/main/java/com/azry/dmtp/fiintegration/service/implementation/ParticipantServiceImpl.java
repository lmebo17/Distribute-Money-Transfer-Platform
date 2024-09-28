package com.azry.dmtp.fiintegration.service.implementation;

import com.azry.dmtp.fiintegration.config.handler.exception.NotFoundException;
import com.azry.dmtp.fiintegration.config.handler.exception.ServiceUnableException;
import com.azry.dmtp.fiintegration.config.handler.exception.UnknownException;
import com.azry.dmtp.fiintegration.service.ParticipantService;
import com.azry.dmtp.z.bankservice.ClientDTO;
import com.azry.dmtp.z.bankservice.api.ClientsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ClientsApi clientsApi;

    @Cacheable(value = "Clients", key = "#personalNumber", condition = "#useCache")
    public ClientDTO getParticipant(Long personalNumber, boolean useCache) {
        try {
            return (clientsApi.getClientByPersonalNumber(personalNumber));
        } catch (ResourceAccessException rae) {
            throw new ServiceUnableException(HttpStatus.SERVICE_UNAVAILABLE.value(), rae.getMessage(), "BankService is not responding");
        } catch (RedisConnectionFailureException rcfe) {
            throw new ServiceUnableException(HttpStatus.SERVICE_UNAVAILABLE.value(), rcfe.getMessage(), "Unable to connect to Redis");
        } catch (RestClientException ex) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Bank Service can't find participant");
        } catch (Exception ex) {
            throw new UnknownException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        }
    }
}
