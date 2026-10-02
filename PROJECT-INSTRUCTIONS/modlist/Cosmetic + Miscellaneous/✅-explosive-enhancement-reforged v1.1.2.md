# Explosive Enhancement: Reforged

## Propriedades do registro

- **Mod:** Explosive Enhancement: Reforged
- **Arquivo JAR:** `explosiveenhancement-neoforge-1.21.1-1.1.2.jar`
- **Versão 1.21.1:** `1.1.2`
- **Categoria:** Visual
- **Função:** Client-only visual enhancer de explosões que substitui/adiciona partículas e efeitos configuráveis sem alterar damage, knockback, block destruction ou explosion radius.
- **Dependências:** Cliente NeoForge 1.21.1. A release física é a variante client-only 1.21/1.21.1; Iron's Spells 3.16.3 está presente e é relevante porque 1.1.2 inclui tempfix para o Creeper Head Projectile.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Runtime físico 1.1.2 é a variante client-only atual. Upstream 1.2.0 amplia configuração e corrige radius-0 explosions e o hook de substituição de partículas para não interferir no restante da explosão de outros mods. A promoção precisa revalidar side/deployment e interação com Iron's, Goety e demais providers de explosão.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/explosive-enhancement-reforged
- **Procedência:** modlist física atual confirma `explosiveenhancement-neoforge-1.21.1-1.1.2.jar` / 1.1.2. CurseForge oficial revalidado em 02/10/2026 confirma 1.2.0 NeoForge para 1.21.1 (file ID 8965905) como release posterior.
- **Observações:** O runtime físico permanece 1.1.2 client-only. A upstream 1.2.0 adiciona config screen/hot reload, size-duration-opacity controls, entity blacklist e particle commands; também corrige radius-0 crashes e altera o replacement para atuar somente sobre partículas vanilla da explosão.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 02/10/2026 — runtime físico permanece 1.1.2. A release 1.2.0 foi comparada e seus deltas relevantes foram incorporados abaixo.
- **Decisão:** Sem decisão
- **Sobreposição:** Sobreposição apenas com outros replacers de partículas/efeitos de explosão. Não é duplicata de TNT, spell, weapon ou physics mods que controlam a explosão lógica.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #270: JAR `explosiveenhancement-neoforge-1.21.1-1.1.2.jar`, mod id `explosiveenhancement`, runtime `1.1.2`, SHA-1 `fee0be3ffe494189733ceaeb7163a38b03098ea5`.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `explosiveenhancement-neoforge-1.21.1-1.1.2.jar` · mod id `explosiveenhancement` · versão `1.1.2` · NeoForge 1.21.1. O arquivo instalado corresponde à linha **client-only** 1.21/1.21.1 publicada pelo projeto.
## 1. Papel no modpack
Explosive Enhancement: Reforged é um replacer/enhancer **visual de explosões**. Ele adiciona partículas e animações de explosão mais elaboradas sem assumir ownership de damage, knockback, block destruction ou explosion radius.
## 2. Authority / ownership
- **Minecraft/mod da explosão:** cria a explosão e define damage/physics/world effects.
- **Explosive Enhancement:** partículas/efeitos client-side derivados do evento visual.
Uma explosão parecer maior ou mais intensa não significa que seu raio lógico mudou.
## 3. Variante client-only
O projeto publica variantes client-only e client+server em algumas linhas. Para 1.21/1.21.1, o arquivo físico `explosiveenhancement-neoforge-1.21.1-1.1.2.jar` corresponde à release client-only.
Logo, a lógica de mundo não deve depender de sua presença no servidor.
## 4. Partículas configuráveis
O projeto permite habilitar/desabilitar tipos de partículas individualmente. Também documenta efeito especial de bolhas em explosões underwater. O config publicado fica em `config/explosiveenhancement.toml`.
Valores/opções concretos devem ser lidos do config da instância; esta ficha não inventa defaults além do que o projeto declara.
## 5. Tempfix Iron's Spells
A changelog da release 1.1.2 registra um **tempfix para client crash causado pelo Creeper Head Projectile de Iron's Spells 'n Spellbooks**. Isso é diretamente relevante porque o pack usa Iron's Spells 3.16.3 e vários addons com explosões/spells.
## 6. Histórico do crash
Relatos upstream anteriores apontavam crash ao gerar partículas em explosões de baixo/zero power, incluindo o spell Lob Creeper/Creeper Head. A release 1.1.2 é a linha instalada com correção temporária específica; não rebaixar sem reabrir esse regression gate.
## 7. Relação com Requiem
Requiem 0.1.7 também possui spells/summon effects com explosões documentadas. Explosive Enhancement deve apenas visualizar as explosões realmente emitidas pelo server/provider; não multiplicar summon death ou damage events.
## 8. Client / Server
Toda lógica do mod instalado é client-facing. Servidor envia os eventos/packets normais de explosão; o cliente cria efeitos visuais. Ausência do mod em outro cliente pode mudar apenas a aparência, não o resultado da explosão.
## 9. Lifecycle
Validar client join/rejoin, explosion vanilla, projectile explosion, underwater explosion, resource reload, config change/restart, shader on/off, dimension change e updates de Iron's Spells.
## 10. Multiplayer
Dois clients podem ver efeitos diferentes. Um client com Explosive Enhancement não deve gerar damage adicional nem afetar o outro player. Crash de partículas deve permanecer isolado ao client e não desconectar por protocol handling.
## 11. Riscos
1. particle crash reaparecer com novo projectile/explosion type;
2. outro mod substituir o mesmo explosion visual hook;
3. shader/particle renderer causar z-fighting ou performance drop;
4. partículas excessivas causarem frame spikes;
5. underwater effect usar contexto incorreto;
6. config antiga conter option removida;
7. client-only file ser confundido com server dependency;
8. visual magnitude ser confundida com damage radius;
9. Requiem/Iron's spell projectile novo expor edge case;
10. update/revert perder o tempfix 1.1.2.
## 12. Matriz de testes
1. TNT/creeper vanilla.
2. Explosion power pequena/zero quando reproduzível com segurança em ambiente de teste.
3. Iron's Lob Creeper/Creeper Head projectile.
4. Chain Creeper/other explosion spell.
5. Requiem summon-death explosion effect.
6. Underwater explosion.
7. Dois clients: um com e outro sem o mod.
8. Config de partículas on/off.
9. Shader profile real do pack.
10. Stress test com muitas explosões e medição de frame time.
**Esta catalogação não afirma que esses testes foram executados.**
## 13. Evidências
- modlist física canônica: JAR/mod id/version/hash;
- página/changelog oficial Explosive Enhancement: cosmetic explosion particles, config e variantes de distribuição;
- changelog 1.1.2: tempfix do crash ligado ao Creeper Head Projectile de Iron's Spells;
- issue upstream do Iron's Spells: crash histórico atribuído ao particle path do Explosive Enhancement.
> **Boundary canônico:** Explosive Enhancement controla somente a **apresentação client-side da explosão**. Damage, knockback e world destruction pertencem ao explosion provider.

## 14. Atualização upstream 1.2.0 — não instalada

A autoridade física continua em **Explosive Enhancement 1.1.2**. A release **1.2.0** para NeoForge 1.21.1 foi publicada em 24/09/2026.

### Configuração e controle
- adiciona tela de configuração in-game no NeoForge;
- adiciona controles de **size, duration e opacity**;
- permite blacklist de entities para manter partículas vanilla;
- alterações de config passam a ser aplicadas sem restart;
- partículas do mod podem ser geradas por comandos com parâmetros de escala/tamanho.

### Correções relevantes
- corrige crash com explosões de **radius 0**, incluindo casos associados a Iron's Spells e Goety;
- explosões de radius 0 preservam partículas vanilla;
- o mod passa a substituir **somente as partículas vanilla da explosão**, em vez de cancelar o restante do processamento visual/evento; o upstream cita que o comportamento anterior podia interferir na finalização de explosões de outros mods, incluindo casos em que block destruction/fire spreading deixavam de ocorrer.

Esse último ponto é tratado como correção de compatibilidade upstream, não como afirmação de que o runtime físico 1.1.2 reproduz obrigatoriamente o problema em todos os providers do pack.

### Gate de promoção 1.1.2 → 1.2.0
- [ ] Confirmar a distribuição esperada da build 1.2.0 em cliente/dedicated server antes de alterar o deployment.
- [ ] TNT/creeper e explosions de radius 0 não causam crash.
- [ ] Iron's Creeper Head/Lob Creeper e explosões Goety mantêm resultado lógico correto.
- [ ] Blacklist preserva partículas vanilla apenas para os entities configurados.
- [ ] Size/duration/opacity e hot config reload não deixam particle state stale.
- [ ] Outros mods que finalizam explosões continuam aplicando block destruction/fire conforme o provider, sem interferência do renderer.
- [ ] Dois clientes com perfis visuais diferentes continuam vendo o mesmo damage/world state server-authoritative.

Fonte upstream: CurseForge Explosive Enhancement 1.2.0 NeoForge 1.21.1, file ID 8965905. Nenhum teste acima foi executado nesta atualização documental.
