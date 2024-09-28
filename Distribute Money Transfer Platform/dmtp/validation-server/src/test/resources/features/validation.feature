Feature: Validate Transfer based on various rules

  Scenario Outline: Validate transfers based on transfer type, currency, country, amount limits, sender and receiver personal numbers
    Given TransferModel is created with "<transferType>", "<mts>", "<currency>", "<country>", "<amount>", "<senderPersonalNumber>", and "<receiverPersonalNumber>"
    When Transfer is being validated
    Then Transfer is "<valid>"

    Examples:
      | transferType | mts           | currency | country | amount | senderPersonalNumber | receiverPersonalNumber | valid |
      | SEND         | moneygram     | USD      | GEO     | 5000   | 1234567890           | 0987654321             | true  |
      | SEND         | moneygram     | GEL      | ENG     | 15000  | 1234567890           | 0987654321             | true  |
      | SEND         | *             | USD      | GEO     | 15001  | 1234567890           | 0987654321             | false |
      | SEND         | western-union | GEL      | GEO     | 30000  | 1234567890           | 0987654321             | true  |
      | SEND         | moneygram     | USD      | USA     | 4000   | 0987654321           | 1234567890             | true  |
      | SEND         | *             | USD      | USA     | 6000   | 0987654321           | 1234567890             | false |
      | SEND         | moneygram     | GEL      | GEO     | 30001  | 1234567890           | 0987654321             | false |
      | SEND         | moneygram     | EUR      | FRA     | 10000  | 1234567890           | 0987654321             | true  |
      | SEND         | moneygram     | EUR      | FRA     | 15001  | 1234567890           | 0987654321             | false |
      | SEND         | moneygram     | EUR      | FRA     | 0      | 1234567890           | 0987654321             | false |
      | RECEIVE      | *             | USD      | *       | 5000   | 0987654321           | 1234567890             | true  |
      | RECEIVE      | moneygram     | GEL      | GEO     | 3000   | 0987654321           | 1234567890             | true  |
      | RECEIVE      | *             | USD      | GEO     | 10000  | 0987654321           | 1234567890             | true  |
      | RECEIVE      | western-union | USD      | GEO     | 10001  | 0987654321           | 1234567890             | false |
      | RECEIVE      | moneygram     | EUR      | GER     | 8000   | 0987654321           | 1234567890             | true  |
      | RECEIVE      | moneygram     | EUR      | GER     | 8000   | 0987654321           | 1234567890             | true  |
      | RECEIVE      | moneygram     | EUR      | GER     | 15000  | 0987654321           | 1234567890             | true  |
      | RECEIVE      | moneygram     | EUR      | GER     | 15001  | 0987654321           | 1234567890             | false |
      | RECEIVE      | *             | USD      | USA     | 3000   | 0987654321           | 1234567890             | true  |
      | RECEIVE      | moneygram     | USD      | USA     | 5001   | 0987654321           | 1234567890             | false |

  Scenario Outline: Validate transfers based on black listed clients
    Given TransferModel is created with "<transferType>", "<mts>", "<currency>", "<country>", "<amount>", "<senderPersonalNumber>", and "<receiverPersonalNumber>"
    And Sender with personal number "<senderPersonalNumber>" is "<blacklisted>"
    When Transfer is being validated
    Then Transfer is "<valid>"

    Examples:
      | transferType | mts       | currency | country | amount | senderPersonalNumber | receiverPersonalNumber | valid | blacklisted |
      | SEND         | moneygram | USD      | GEO     | 5000   | 42806809693          | 0987654321             | false | true        |
      | SEND         | moneygram | USD      | GEO     | 5000   | 19001111290          | 19001111291            | true  | false       |

  Scenario Outline: Validate transfers based on limits and black listed clients
    Given TransferModel is created with "<transferType>", "<mts>", "<currency>", "<country>", "<amount>", "<senderPersonalNumber>", and "<receiverPersonalNumber>"
    And Sender with personal number "<senderPersonalNumber>" is "<blacklisted>"
    When Transfer is being validated
    Then Transfer is "<valid>"

    Examples:
      | transferType | mts       | currency | country | amount | senderPersonalNumber | receiverPersonalNumber | valid | blacklisted |
      | SEND         | moneygram | USD      | GEO     | 5000   | 19001111290          | 19001111291            | true  | false       |
      | SEND         | moneygram | USD      | GEO     | 5001   | 19001111290          | 19001111291            | false | false       |

