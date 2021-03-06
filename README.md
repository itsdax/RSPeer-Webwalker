# RSPeer-Webwalker

Fast and customizable webwalker for RSPeer. Instantly calculates a path to your destination and walks it. Accounts for path requirements (quests, items, level), teleports, and shortcuts. Handles script pauses and stops. Dynamic walking conditions and exit conditions. Features collision aware randomness when selecting which tiles to walk on.

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
This condition will be checked in between walks and idle actions.
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
The following links will direct you to where to look for contributing to the Walker. Create a pull request and I'll look it over. Thanks for contributing!

```java
package com.dax.walker.engine.definitions;
```

- [Adding new Pop-Up interface](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/PopUpInterfaces.java)

![](https://i.imgur.com/ip19tvk.png)


- [Adding new Teleport Method](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/Teleport.java)

![](https://i.imgur.com/Jp0wewr.png)

- [Adding new Wearable Teleport Item](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/WearableItemTeleport.java)

![](https://i.imgur.com/nkqApnQ.png)

- [Updating Stronghold Answers](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/StrongHoldAnswers.java)

![](https://i.imgur.com/XJfCXqI.png)


- [Adding Path Links](https://github.com/itsdax/RSPeer-Webwalker/blob/master/com/dax/walker/engine/definitions/PathLink.java)

![](https://i.imgur.com/KvfHUsz.png)
