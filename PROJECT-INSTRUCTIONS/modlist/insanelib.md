# InsaneLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b58ac2d6576d2a5866
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** InsaneLib
- **Arquivo JAR:** `insanelib-2.4.32.0.jar`
- **Versão 1.21.1:** 2.4.32.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca e framework compartilhado dos mods de Insane96, com módulos/config declarativos, utilitários, tags/NBT, atributos e pequenas correções/features comuns de gameplay.
- **Dependências:** NeoForge 1.21.1. Source exato 2.4.32.0 foi desenvolvido contra NeoForge 21.1.219; pack usa 21.1.248. Consumers da família Insane devem ser validados antes de remover/atualizar a library.
- **Sobreposição:** Não substitui outras libraries. Algumas features comuns podem tocar tempo/weather, atributos, XP e entity NBT, mas a authority final deve permanecer no sistema que efetivamente aplica cada regra; evitar handlers duplicados.
- **Compatibilidade/Riscos:** Biblioteca com hooks comuns e features opcionais que podem alterar tempo, weather, NBT de entidades, XP e atributos. Riscos: ABI drift em consumers, tags/NBT malformadas, sound IDs inválidos, duplicate handlers, config divergence e mudanças em time/weather compartilhadas com outros sistemas.
- **Observações:** 2.4.32.0 adiciona Sound Overrides via NBT para fuse/explosion. 2.4.31.0 documenta `insanelib:mob_detection_range`, `insanelib:push_resistance`, correção do XP do Grindstone e ajustes técnicos em ModNBTData/MCUtils.
- **Procedência:** modlist.txt física atual + CurseForge oficial InsaneLib 2.4.32.0 file 8820884 + source oficial Insane96/InsaneLib branch 1.21.1 com mod_version 2.4.32.0 + changelog exato.
- **Fonte:** https://github.com/Insane96/InsaneLib/tree/1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — InsaneLib 2.4.32.0 source-pinned; módulos/config, tags/NBT, Sound Overrides 2.4.32.0, atributos/fixes 2.4.31.0, client/server, lifecycle, riscos e matriz de testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `insanelib-2.4.32.0.jar`, mod id `insanelib`, versão `2.4.32.0`. O source oficial `Insane96/InsaneLib:1.21.1` declara exatamente `mod_version=2.4.32.0`, Minecraft 1.21.1 e NeoForge 21.1.219; o pack usa NeoForge 21.1.248.

## 1. Papel e authority
InsaneLib é a biblioteca compartilhada dos mods de Insane96 e também contém pequenas features/fixes comuns. Ela é authority apenas das APIs, módulos e comportamentos que registra diretamente; mods consumidores continuam owners de seu gameplay específico.

## 2. Framework de módulos e configuração
A documentação oficial descreve features modulares, cada uma com subconfig própria e opções declaradas via `@Config`. Também existe `JsonFeature`, capaz de criar/consumir JSONs na pasta de configuração. Isso torna config/schema e reload/restart boundaries relevantes para consumers.

## 3. Features de jogador documentadas
Entre as features públicas está a opção de interromper day/game time quando não há jogadores online. O comportamento também pode pausar weather e, quando presente, o ciclo de Serene Seasons; a documentação também cita compatibilidade com Time Control. Não inferir que essa feature esteja habilitada sem ler a config efetiva.

## 4. Tags/NBT comuns
O upstream documenta dados como spawn type, flag de explosão causar fogo e multiplicador de XP em entidades. Integrações externas não devem escrever essas tags sem conhecer seu contrato, pois valores duplicados ou tipos errados podem alterar morte/explosão de forma global.

## 5. Sound Overrides — 2.4.32.0
A release instalada adiciona override de sons de fuse e explosão via NBT. Os paths publicados são `NeoForgeData.insanelib.sound_overrides.fuse_sound` e `NeoForgeData.insanelib.sound_overrides.explosion_sound`. O valor deve apontar para sound IDs válidos; ausência deve degradar ao comportamento normal do provider.

## 6. Atributos e fixes — 2.4.31.0
A linha imediatamente anterior adicionou traduções para `insanelib:mob_detection_range` e `insanelib:push_resistance`; também corrigiu a posição de saída de XP do Grindstone e registrou ajustes técnicos em `ModNBTData` e `MCUtils#getGrindstoneOutputPos`. Esses pontos são regression gates válidos da linha 2.4.32.0.

## 7. Client / server
A distribuição oficial é Client & Server. Config e state que alteram tempo, weather, XP, atributos, explosões ou entity data devem ser authoritative no servidor. Sons/feedback visuais são apresentados no cliente, mas o evento de fuse/explosion nasce do state real da entidade.

## 8. Consumers e ABI
Atualizar InsaneLib é uma mudança potencial de ABI para qualquer mod consumidor. A presença física de uma library não autoriza removê-la por falta de conteúdo visível; remover ou trocar versão exige mapear dependencies reais na metadata dos consumers.

## 9. Lifecycle
Validar construction/config load, datapack/config reload quando suportado, login/logout do último jogador, save/restart, entity spawn/death, explosões, Grindstone interaction e reconnect. State transitório não deve sobreviver indevidamente a unload ou troca de mundo.

## 10. Riscos técnicos
- consumer compilado contra assinatura/API diferente;
- configuração divergente entre ambientes;
- `JsonFeature` receber schema inválido;
- tags/NBT custom com tipo ou resource location inválido;
- override de som quebrar por sound ID ausente;
- dois mods aplicarem o mesmo ajuste de XP/explosão;
- feature de tempo/weather competir com outro provider;
- atributo não ser removido ou sincronizado corretamente;
- tratar NeoForge 21.1.219 de desenvolvimento como versão exigida exata apesar do range/runtime físico usar 21.1.248.

## 11. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com InsaneLib 2.4.32.0 e consumers físicos.
- [ ] Consumers carregam sem linkage/mixin errors.
- [ ] Config default não altera features que deveriam estar desativadas.
- [ ] Sound Override válido substitui fuse/explosion uma única vez.
- [ ] Sound ID inválido/ausente falha de modo seguro.
- [ ] Entity XP multiplier não duplica recompensa.
- [ ] Grindstone XP aparece na posição correta.
- [ ] Login do primeiro/logout do último jogador respeita config de time stop.
- [ ] Restart/reconnect não deixa state transitório residual.

## 12. Evidências e limites
- **Modlist física:** filename/mod id/version exatos.
- **CurseForge oficial:** release 2.4.32.0 e descrição da biblioteca.
- **Source oficial:** branch 1.21.1 pinada em `mod_version=2.4.32.0`.
- **Changelog:** Sound Overrides 2.4.32.0 e mudanças 2.4.31.0 citadas acima.
- **Limite:** consumers físicos não foram inferidos somente pela autoria; devem ser confirmados por dependency metadata quando necessário.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
