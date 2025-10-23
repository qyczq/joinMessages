# Photos:
## 1.20.4 client on 1.20.4 server
![image](https://github.com/qyczq/joinMessages/assets/120599733/8b51a558-700d-4a05-98b6-f493b0059f01)
## 1.18 client on 1.20.4 server
![image](https://github.com/qyczq/joinMessages/assets/120599733/85b4f641-2cdb-466a-b606-ded0e74a170d)
## Join message
![image](https://github.com/qyczq/joinMessages/assets/120599733/b99140f8-8d54-43c4-9d15-ca4ee385bab8)

# Config:
```yml
titles:
  # Main title animation change time in ms
  delay: 500
  # you can do only one too
  main:
    - "<gradient:#61BC00:#BAEC00:#61BC00><b>Survival</b></gradient>1"
    - "<gradient:#61BC00:#BAEC00:#61BC00><b>Survival</b></gradient>2"
    - "<gradient:#61BC00:#BAEC00:#61BC00><b>Survival</b></gradient>3"
    - "<gradient:#61BC00:#BAEC00:#61BC00><b>Survival</b></gradient>4"
  # ViaVersion support
  viaver: true
  # TRUE
  supported-version: "<#1EFF00>ℹ</#1EFF00> <#C8C8C8>Twój klient jest zgodny z aktualną wersją serwera.</#C8C8C8>"
  old-version: "<#FE0000>ℹ</#FE0000> <#FB5454>Twoja wersja może być niekompatybilna z naszym serwerem. Zalecamy aktualizację.</#FB5454>"
  # FALSE
  subtitle: "<#55ff00>Welcome back!</#55ff00>"
messages:
  # Leave join and quit empty for no custom messages
  # %player_name% is a placeholder from PlaceholderAPI, papi available only in join and quit messages and  motd lines
  join: "<#F8EE00>%player_name% dołączył do gry"
  quit: "<#F8EE00>%player_name% opuścił grę"
  reload: "<#F8EE00>Przeładowano konfigurację pluginu."
motd:
  enabled: true
  # Don't change api if you don't know what are you doing.
  # Must be 8x8, placeholders: %uuid% for uuid, %nick% for player name
  api: "https://api.mineatar.io/face/%uuid%?scale=0"
  lines:
    - "1"
    - "2"
    - "3"
    - "4"
    - "5"
    - "6"
    - "7"
    - "8"
sounds:
  enabled: true
  sound: ENTITY_PLAYER_LEVELUP
  # true = to all players, false = to player joining
  global: true
  volume: 2
  pitch: 1
```
# Dependencies:
```yml
softdepend: [PlaceholderAPI, ViaVersion]
```
