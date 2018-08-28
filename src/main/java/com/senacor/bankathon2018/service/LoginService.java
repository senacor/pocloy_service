package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import io.vavr.control.Try;
import me.figo.FigoException;
import me.figo.internal.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

  private final static Logger LOG = LoggerFactory.getLogger(LoginService.class);
  private final FigoConnector figoConnector;
  private final AxwayService axwayService;

  public LoginService(FigoConnector figoConnector, AxwayService axwayService) {
    this.figoConnector = figoConnector;
    this.axwayService = axwayService;
  }

  public boolean isLoginViable(Credentials credentials) {
    //TODO More Error Handling?
    Try<TokenResponse> figoLoginResponse = figoConnector.figoLogin(credentials);
    Try<String> axwayLoginResponse = axwayService.loginAndObtainSession(credentials);

    figoLoginResponse.onSuccess(token -> LOG.info("Login into Figo was successful"));
    figoLoginResponse.onFailure(token -> LOG.info("Login into Axway was unsuccessful"));
    axwayLoginResponse.onSuccess(session -> LOG.info("Login into Axway was successful"));
    axwayLoginResponse.onFailure(session -> LOG.info("Login into Axway was unsuccessful"));

    return figoLoginResponse.isSuccess() && axwayLoginResponse.isSuccess();
  }

  private void printErrMsg(Throwable throwable) {
    throwable.printStackTrace();
    if (throwable instanceof FigoException) {
      FigoException figoError = (FigoException) throwable;
      LOG.info(figoError.getErrorDescription());
    } else {
      LOG.info(throwable.getMessage());
    }
  }

  public Try<String> obtainAccessToken(Credentials credentials) {
    Try<TokenResponse> tokenResponses = figoConnector.figoLogin(credentials);
    return tokenResponses.map(res -> res.access_token);
  }

}
