# Configuring Blowable Obby

There are two configuration files created at Startup,

1. [Config.json](#configuring-your-plugin)
2. Lang.json

## Configuring your Plugin

The configuration file is split in two main sections,
[Explosions](#configuring-explosions) configuration, and [Blocks](#configuring-blocks) configuration,
this will let you mix up and add up how explosions will behave
against certain blocks, and vice-versa.

As a heads-up, you should copy-paste the file, and rename it to
`config.old.json`, in case yours get corrupted, you can always change it back.
(We don't automatically do that) (...yet)

Here's how it should look in your config file:

```json
{
  "lang": "en",
  "explosions":  "/** Removed for brevity */",
  "blocks": "/** Removed for brevity */"
}
```

### Configuring Explosions

Explosions can be configured in two ways, easy one, using the global values,
`radius` and `damage`. This will be applied to every explosion, configuring it's damage dealt to a block,
and the radius the explosion should damage blocks.

```json
{
  "explosions": {
    "radius": 3.0,
    "damage": 1.0
  }
}
```

> Please notice how each value holds a decimal, make sure yours does too.

Referring that:
1. Each explosion will damage a radius of 3 blocks from where the explosion initiated.
2. The first radius will receive a total of 1 hit point damage, while the following 2, will receive 50% and 33% of 1, accordingly.

Moreover, we can modify this for certain types of explosions,
allowing wither skulls deal more damage than they usually do,
useful for HCF or OP Factions.

Here's how:

```json
{
  "explosions": {
    "custom": {
      "WITHER_SKULL": {
        "radius": 1.0,
        "damage": 1.0,
        "enabled": true,
        "disabledWorlds": []
      }
    }
  }
}
```

> BEWARE: `radius` and `damage` here, do not specify a fixed value, instead, they "boost" the global values.

In our `custom` section under explosions, you can add up as many configurations as you would like.
However, only one per explosion type is allowed, otherwise it will break.

You can configure the radius and damage caused by each kind of explosion, by giving it a multiplier value for the global config.
Yes, that means the value indicated here, is multiplied against the global value. (So it doesn't gives back 1 (Dumb math)).

As well, you are allowed to disable the behavior, or "boost" within specific worlds, by passing an array of the world names you wouldn't like it to work,
in the `disabledWorlds` section:

```json
{
  "custom": {
    "WITHER_SKULL": {
      "radius": 1.0,
      "damage": 3.0,
      "enabled": true,
      "disabledWorlds": [
        "pls_no_enable_it_here_world",
        "don_t_enable_it_here_either_world"
      ]
    }
  }
}
```

### Configuring blocks

Well, I couldn't think off many options for this boys, so here's the basics:

```json
{
  "blocks": {
    "BEDROCK": {
      "health": 64.0,
      "except": ["CREEPER"]
    }
  }
}
```

Where, health is- well, the health of the block. And exclude, is an optional array of the explosion types which do not harm the block.

And well, that's basically most of what you can configure so far.