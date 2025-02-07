<img src="https://raw.githubusercontent.com/amateras-server/ama-carpet/main/src/main/resources/assets/ama-carpet/icon_alpha_white.png" width="256" style="display: block; margin: auto;">

# AmaCarpet

[日本語の説明はこちら](README_ja.md)

[![Dev Builds](https://github.com/amateras-server/ama-carpet/actions/workflows/gradle.yml/badge.svg)](https://github.com/amateras-server/ama-carpet/actions/workflows/gradle.yml)
[![License](https://img.shields.io/github/license/amateras-server/ama-carpet.svg)](https://opensource.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/amateras-server/ama-carpet.svg)](https://github.com/amateras-server/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)
[![Discord](https://img.shields.io/discord/1157213775791935539)](https://discord.gg/YFJff2Bkx8)

A carpet addition made for **Amateras SMP**.


## Rules


### cheatRestriction

> Prohibits specific features in client-side mods such as [Tweakeroo](https://modrinth.com/mod/tweakeroo), [Tweakermore](https://modrinth.com/mod/tweakermore), [Litematica](https://modrinth.com/mod/litematica). The features to restrict can be configured using the `/restriction` command.

> [!NOTE]
> Only works in server side.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### commandListRestriction

> Enables `/listrestriction` command to display features restricted by [cheatRestriction](#cheatrestriction).
> `/listrestriction` command provides the same output as `/restriction` when used with no arguments.
> However, unlike `/restriction`, this command can be executed by players without operator permissions by default.

- Type: `String`
- Default value: `true`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`
<br><br>

### commandRestriction

> Enables `/restriction` command to configure features restricted by [cheatRestriction](#cheatrestriction).

- Type: `String`
- Default value: `ops`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`
<br><br>

### debugModeAmaCarpet

> Enables debug print for AmaCarpet developer.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`
<br><br>

### disableAnimalSpawnOnChunkGen

> Disables animal spawning during chunk generation.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `OPTIMIZATION`, `SURVIVAL`
<br><br>

### disableSoundEngine

> Disables all server-side sound engine processes.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `CREATIVE`, `OPTIMIZATION`, `SURVIVAL`
<br><br>

### endGatewayChunkLoad (MC < 1.21)

> Allows entities traveling through end gateway portals to load a 3x3 chunk area, similar to nether portals.
> This is a backport of a feature implemented in Minecraft 1.21.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### endPortalChunkLoad (MC < 1.21)

> Allows entities traveling through end portals to load a 3x3 chunk area, similar to nether portals.
> This is a backport of a feature implemented in Minecraft 1.21.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### notifyKyoyu
> Sends notifications to player's chat when a schematic is shared or unshared using [kyoyu](https://modrinth.com/plugin/kyoyu).

> [!NOTE] 
> Only works in server side.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### notifySyncmatica
> Sends notifications to player's chat when a schematic is shared or unshared using [syncmatica](https://modrinth.com/mod/syncmatica).

> [!NOTE]
> Only works in server side.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### reloadPortalTicket

> Reloads all Nether portal chunk-loading tickets during server startup.
> This ensures that chunk loaders remain functional after server restarts.
> This feature is a backport of the implementation in snapshot 25w05a.

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`
<br><br>

### requireAmaCarpetClient

> Prevents clients without AmaCarpet installed from logging in.
> The timeout duration can be configured with [requireAmaCarpetClientTimeoutSeconds](#requireamacarpetclienttimeoutseconds).

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`
<br><br>

### requireAmaCarpetClientTimeoutSeconds

> Determines timeout duration for [requireAmaCarpetClient](#requireamacarpetclient) to check if the client has ama-carpet on login phase within the range 1 to 180 seconds.

- Type: `int`
- Default value: `5`
- Suggested options: `3`, `5`, `10`
- Categories: `AMA`
<br><br>
