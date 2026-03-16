<img src="https://raw.githubusercontent.com/amateras-server/ama-carpet/main/src/main/resources/assets/ama-carpet/icon_alpha_white.png" width="256" style="display: block; margin: auto;">

# AmaCarpet

[English](README.md)

[![Dev Builds](https://github.com/amateras-server/ama-carpet/actions/workflows/gradle.yml/badge.svg)](https://github.com/amateras-server/ama-carpet/actions/workflows/gradle.yml)
[![License](https://img.shields.io/github/license/amateras-server/ama-carpet.svg)](https://opensource.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/amateras-server/ama-carpet.svg)](https://github.com/amateras-server/ama-carpet/issues)
[![Modrinth](https://img.shields.io/modrinth/dt/amacarpet?label=Modrinth%20Downloads)](https://modrinth.com/mod/amacarpet)
[![Discord](https://img.shields.io/discord/1377258772166348890)](https://discord.gg/MR878KhsCj)

**Amateras SMP**のために作られたcarpet addition。<br>
現在は1.19から1.21.11に対応。

## Dependencies
- [carpet](https://modrinth.com/mod/carpet) (必須)
- [fabric-api](https://modrinth.com/mod/fabric-api) (必須)
- [amatweaks](https://modrinth.com/mod/amatweaks) (任意)
- [litematica](https://modrinth.com/mod/litematica) (任意)
- [malilib](https://modrinth.com/mod/malilib) (任意)
- [syncmatica](https://modrinth.com/mod/syncmatica) (任意)
- [tweakermore](https://modrinth.com/mod/tweakermore) (任意)
- [tweakeroo](https://modrinth.com/mod/tweakeroo) (任意)


## Rules


### cheatRestriction

> [tweakeroo](https://modrinth.com/mod/tweakeroo)や[tweakermore](https://modrinth.com/mod/tweakermore)、[litematica](https://modrinth.com/mod/litematica)、[amatweaks](https://modrinth.com/amatweaks)などにのクライアントmodに含まれる指定された機能を禁止する。禁止する機能は`/restriction`コマンドで指定できる。引数なしで`/restriction`(op)か`/listrestriction`を実行することにより設定可能な項目の一覧を確認可能。

> [!NOTE]
> サーバー側でのみ動作する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`<br>

### commandListRestriction

> `/listrestriction`コマンドを有効化し、[cheatRestriction](#cheatRestriction)により禁止されているクライアントmodの機能の一覧をこのコマンドで閲覧できるようにする。
> デフォルトでは`/restriction`コマンドを引数なしで実行したときと同じ結果を、op権限を持っていないプレイヤーでも確認することができる。

- Type: `String`
- Default value: `true`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`<br>

### commandRestriction

> `/restriction`コマンドを有効化し、[cheatRestriction](#cheatRestriction)により禁止するクライアントmodの機能をこのコマンドで設定できるようにする。

- Type: `String`
- Default value: `ops`
- Suggested options: `true`, `false`, `ops`, `0`, `1`, `2`, `3`, `4`
- Categories: `AMA`, `COMMAND`, `SURVIVAL`<br>

### disableAnimalSpawnOnChunkGen

> 地形生成時の動物のスポーンを無効化する。軽量化やワールドサイズの縮小が期待される。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `EXPERIMENTAL`, `SURVIVAL`<br>

### disableSoundEngine

> サーバー側で処理されるすべての音エンジンを無効化して軽量化する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `CREATIVE`, `OPTIMIZATION`, `SURVIVAL`<br>

### notifySyncmatica

> schematic(.litematicファイル)が[syncmatica](https://modrinth.com/mod/syncmatica)を通じて共有されたり、削除されたときにプレイヤーのチャットへ通知を送信する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`<br>

### reloadPortalTicket (MC < 1.21.5)

> サーバーの起動時に、すべての記録されていたポータルチャンクロードチケットを再読み込みさせる。
> これによりチャンクローダーがサーバーの再起動で壊れなくなる。
> 1.21.5で実装された機能のbackport。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`, `SURVIVAL`<br>

### requireAmaCarpetClient

> ama-carpetを導入していないクライアントによるサーバーへのログインを拒否する。

- Type: `boolean`
- Default value: `false`
- Suggested options: `false`, `true`
- Categories: `AMA`<br>

