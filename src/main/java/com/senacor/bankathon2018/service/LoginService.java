package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import io.vavr.control.Try;
import me.figo.internal.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

  private final static Logger LOG = LoggerFactory.getLogger(LoginService.class);
  private final FigoConnector figoConnector;

  public LoginService(FigoConnector figoConnector) {
    this.figoConnector = figoConnector;
  }

  public boolean isLoginViable(Credentials credentials) {
    //TODO More Error Handling?
    Try<TokenResponse> tokenResponses = figoConnector.figoLogin(credentials);
    if (tokenResponses.isSuccess()) {
      LOG.info("Login was successful");
      return true;
    } else {
      LOG.info("Login was unsuccessful: ", tokenResponses.getCause());
      tokenResponses.getCause().printStackTrace();
      return false;
    }
  }

  public Try<String> obtainAccessToken(Credentials credentials) {
    Try<TokenResponse> tokenResponses = figoConnector.figoLogin(credentials);
    return tokenResponses.map(res -> res.access_token);
  }

}
