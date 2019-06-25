# RSPeer-Webwalker

Fast and customizable webwalker for RSPeer. Calculates a path to your destination and walks it. Accounts for path requirements, teleports, and shortcuts.

#### Creating DaxWalker instance
```java
DaxWalker daxWalker = new DaxWalker(new Server("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY"));
```
###### [This example uses a **PUBLIC** key, shared across hundreds of users. You can get your own dedicated keys here.](https://admin.dax.cloud/)



#### Walking to location
This will check your available teleports and use if deemed necessary. 
The cost of a teleport versus walking the distance is defined in ```com.dax.walker.engine.definitions.Teleport```
```java
daxWalker.walkTo(new Position(1, 2, 3));
```

#### Walking to bank
```java
daxWalker.walkToBank();
```

#### Walking to specific bank
```java
daxWalker.walkToBank(RSBank.VARROCK_EAST);
```

#### Adding Custom Stopping Conditions/Passive Actions
```java
daxWalker.walkTo(new Position(3145, 9914, 0), () -> {
    if (Players.getLocal().getHealthPercent() < 20) {
        Food.eat();
    }
    return false; // false to continue walking after check. true to exit out of walker.
});
```


#### Disabling Teleports
```java
daxWalker.setUseTeleports(false);
```

# Contributing
The following links will direct you to where to look for contributing to the Walker.

#### - [Adding new Pop-Up interface](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/RSPopUp.java)
```java
package com.dax.walker.engine.definitions
```
![](https://i.imgur.com/ip19tvk.png)


#### - [Adding new Teleport Method](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/Teleport.java)
```java
package com.dax.walker.engine.definitions;
```
![](https://i.imgur.com/Jp0wewr.png)

#### - [Adding new Wearable Teleport Item](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/WearableItemTeleport.java)
```java
package com.dax.walker.engine.definitions;
```
![](https://i.imgur.com/nkqApnQ.png)

#### - [Updating Stronghold Answers](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/StrongHoldAnswers.java)
```java
package com.dax.walker.engine.definitions;
```
![](https://i.imgur.com/XJfCXqI.png)


#### - [Adding Path Links](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/PathLink.java)
```java
package com.dax.walker.engine.definitions;
```
![](https://i.imgur.com/KvfHUsz.png)
