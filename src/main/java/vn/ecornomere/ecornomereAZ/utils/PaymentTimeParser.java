package vn.ecornomere.ecornomereAZ.utils;

import vn.ecornomere.ecornomereAZ.model.Enum.PaymentMethod;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PaymentTimeParser {
    public static LocalDateTime parse(String time, PaymentMethod gateway) {

        if (gateway == PaymentMethod.COD) {
            return null; // COD không có paymentTime
        }

        if (time == null || time.isBlank()) {
            return null;
        }

        return switch (gateway) {

            case MOMO -> Instant.ofEpochMilli(Long.parseLong(time))
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            case VNPAY -> LocalDateTime.parse(
                    time,
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            );

            default -> null;
        };
    }

}
