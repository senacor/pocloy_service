package com.senacor.bankathon2018;

import com.senacor.bankathon2018.service.model.LoyaltyContent;
import java.util.Calendar;
import java.util.Random;
import org.junit.Test;

public class ContentSelection {

  @Test
  public void testrandom() {
    int pick = 1 + new Random(Calendar.getInstance().getTimeInMillis())
        .nextInt(LoyaltyContent.values().length);
    LoyaltyContent surpriseContent = LoyaltyContent.values()[pick];
    System.out.println(surpriseContent.name());
  }

}
