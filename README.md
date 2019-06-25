# RSPeer-Webwalker

### Creating DaxWalker instance
```
DaxWalker daxWalker = new DaxWalker(new Server("sub_DPjXXzL5DeSiPf", "PUBLIC-KEY"));
```
###### [This example uses a **PUBLIC** key, shared across hundreds of users. You can get your own dedicated keys here.](https://admin.dax.cloud/)



### Walking to location
This will check your available teleports and use if deemed necessary. 
The cost of a teleport versus walking the distance is defined in ```com.dax.walker.engine.definitions.Teleport```

```
daxWalker.walkTo(new Position(1, 2, 3));
```

### Walking to bank
```
daxWalker.walkToBank();
```

### Walking to specific bank
```
daxWalker.walkToBank(RSBank.VARROCK_EAST);
```

### Adding Custom Stopping Conditions/Passive Actions
```
daxWalker.walkTo(new Position(3145, 9914, 0), () -> {
    if (Players.getLocal().getHealthPercent() < 20) {
        Food.eat();
    }
    return false; // false to continue walking after check. true to exit out of walker.
});
```


### Disabling Teleports
```
daxWalker.setUseTeleports(false);
```
