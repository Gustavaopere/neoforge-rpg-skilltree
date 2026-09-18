# InsaneLib

## Propriedades do registro

- **Mod:** InsaneLib
- **Arquivo JAR:** insanelib-2.4.33.0.jar
- **Versão 1.21.1:** 2.4.33.0
- **Categoria:** Biblioteca
- **Função:** Biblioteca e framework compartilhado dos mods de Insane96, com módulos/config declarativos, utilitários, tags/NBT, atributos e pequenas correções/features comuns de gameplay.
- **Dependências:** NeoForge 1.21.1; pack físico atual usa NeoForge 21.1.250. O JAR físico 2.4.33.0 embarca `EvalEx-3.6.0.jar` via JarJar como dependência interna do host. Consumers da família Insane devem ser validados antes de remover/atualizar a library.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Biblioteca com hooks comuns e features opcionais que podem alterar tempo, weather, NBT de entidades, XP e atributos. Riscos: ABI drift em consumers, tags/NBT malformadas, duplicate handlers, config divergence, mudanças em time/weather compartilhadas com outros sistemas e consumer ainda depender do Sound Overrides removido em 2.4.33.0.
- **Fonte:** https://github.com/Insane96/InsaneLib/tree/1.21.1
- **Procedência:** modlist(1).txt física atual de 18/09/2026 + CurseForge oficial InsaneLib 2.4.33.0, release NeoForge 1.21.1 de 15/09/2026 + changelog oficial 2.4.33.0/2.4.32.0 + inventário físico confirmando `EvalEx-3.6.0.jar` em `META-INF/jarjar`.
- **Observações:** 2.4.33.0 remove Sound Overrides, recurso que a 2.4.32.0 havia adicionado para fuse/explosion via NBT. O JAR continua hospedando `EvalEx 3.6.0` internamente; essa biblioteca não recebe entrada top-level. As mudanças 2.4.31.0 em `insanelib:mob_detection_range`, `insanelib:push_resistance`, Grindstone XP e ModNBTData/MCUtils permanecem baseline histórica.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 18/09/2026 — runtime físico reconciliado para `insanelib-2.4.33.0.jar` / `2.4.33.0`. A release oficial de 15/09/2026 remove Sound Overrides, recurso introduzido na 2.4.32.0. `EvalEx 3.6.0` continua embarcado em `META-INF/jarjar` e permanece dependência interna, não top-level.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 2026-09-18 — runtime físico atualizado para 2.4.33.0; nenhuma decisão curatorial nova. Sound Overrides deixa de ser feature ativa porque foi removido pelo upstream nesta release.
- **Sobreposição:** Não substitui outras libraries. Algumas features comuns podem tocar tempo/weather, atributos, XP e entity NBT, mas a authority final deve permanecer no sistema que efetivamente aplica cada regra; evitar handlers duplicados.
- **Data da última decisão:** 2026-08-30

> 🧩 **ESCOPO CANÔNICO.** Runtime físico: `insanelib-2.4.33.0.jar`, mod id `insanelib`, versão `2.4.33.0`, NeoForge 1.21.1. A release oficial 2.4.33.0 foi publicada em 15/09/2026; o pack físico atual usa NeoForge 21.1.250.
## 1. Papel e authority
InsaneLib é a biblioteca compartilhada dos mods de Insane96 e também contém pequenas features/fixes comuns. Ela é authority apenas das APIs, módulos e comportamentos que registra diretamente; mods consumidores continuam owners de seu gameplay específico.
## 2. Framework de módulos e configuração
A documentação oficial descreve features modulares, cada uma com subconfig própria e opções declaradas via `@Config`. Também existe `JsonFeature`, capaz de criar/consumir JSONs na pasta de configuração. Isso torna config/schema e reload/restart boundaries relevantes para consumers.
O JAR físico `insanelib-2.4.33.0.jar` embarca `META-INF/jarjar/EvalEx-3.6.0.jar`. Pelo protocolo do catálogo, **EvalEx 3.6.0 é dependência interna JarJar do InsaneLib**, não um mod top-level da modlist. Atualizações do host podem trocar essa biblioteca sem aparecer um novo arquivo em `mods`; erros de expression parsing/evaluation devem considerar esse runtime embarcado antes de atribuir causalidade a outro provider.
## 3. Features de jogador documentadas
Entre as features públicas está a opção de interromper day/game time quando não há jogadores online. O comportamento também pode pausar weather e, quando presente, o ciclo de Serene Seasons; a documentação também cita compatibilidade com Time Control. Não inferir que essa feature esteja habilitada sem ler a config efetiva.
## 4. Tags/NBT comuns
O upstream documenta dados como spawn type, flag de explosão causar fogo e multiplicador de XP em entidades. Integrações externas não devem escrever essas tags sem conhecer seu contrato, pois valores duplicados ou tipos errados podem alterar morte/explosão de forma global.
## 5. Sound Overrides — introduzido em 2.4.32.0 e removido em 2.4.33.0
A 2.4.32.0 introduziu override de sons de fuse e explosão via NBT nos paths `NeoForgeData.insanelib.sound_overrides.fuse_sound` e `NeoForgeData.insanelib.sound_overrides.explosion_sound`. A release física **2.4.33.0 removeu integralmente esse recurso**, porque o upstream considerou que suportá-lo corretamente exigiria trabalho excessivo. Portanto esses paths ficam preservados apenas como histórico de versão e **não devem ser tratados como contrato ativo no runtime 2.4.33.0**.
## 6. Atributos e fixes — 2.4.31.0
A linha imediatamente anterior adicionou traduções para `insanelib:mob_detection_range` e `insanelib:push_resistance`; também corrigiu a posição de saída de XP do Grindstone e registrou ajustes técnicos em `ModNBTData` e `MCUtils#getGrindstoneOutputPos`. Esses pontos permanecem regression gates válidos da linha 2.4.33.0.
## 7. Client / server
A distribuição oficial é Client & Server. Config e state que alteram tempo, weather, XP, atributos, explosões ou entity data devem ser authoritative no servidor. Feedback audiovisual permanece client-facing quando aplicável, mas a antiga superfície Sound Overrides não existe mais em 2.4.33.0; eventos de fuse/explosion continuam derivados do state real da entidade e dos providers correspondentes.
## 8. Consumers e ABI
Atualizar InsaneLib é uma mudança potencial de ABI para qualquer mod consumidor. A presença física de uma library não autoriza removê-la por falta de conteúdo visível; remover ou trocar versão exige mapear dependencies reais na metadata dos consumers.
## 9. Lifecycle
Validar construction/config load, datapack/config reload quando suportado, login/logout do último jogador, save/restart, entity spawn/death, explosões, Grindstone interaction e reconnect. State transitório não deve sobreviver indevidamente a unload ou troca de mundo.
## 10. Riscos técnicos
- consumer compilado contra assinatura/API diferente;
- configuração divergente entre ambientes;
- `JsonFeature` receber schema inválido;
- tags/NBT custom com tipo ou resource location inválido;
- consumer/config antigo ainda depender de Sound Overrides removido em 2.4.33.0;
- dois mods aplicarem o mesmo ajuste de XP/explosão;
- feature de tempo/weather competir com outro provider;
- atributo não ser removido ou sincronizado corretamente;
- tratar um NeoForge de desenvolvimento histórico como versão exigida exata apesar do runtime físico atual usar 21.1.250.
## 11. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com InsaneLib 2.4.33.0 e consumers físicos.
- [ ] Consumers carregam sem linkage/mixin errors.
- [ ] Config default não altera features que deveriam estar desativadas.
- [ ] Nenhum consumer/config do pack depende da antiga superfície Sound Overrides da 2.4.32.0.
- [ ] Fuse/explosion continuam funcionais sem os paths removidos de Sound Overrides.
- [ ] Entity XP multiplier não duplica recompensa.
- [ ] Grindstone XP aparece na posição correta.
- [ ] Login do primeiro/logout do último jogador respeita config de time stop.
- [ ] Restart/reconnect não deixa state transitório residual.
## 12. Evidências e limites
- **Modlist física:** filename/mod id/version exatos e `EvalEx-3.6.0.jar` embarcado em `META-INF/jarjar`.
- **CurseForge oficial:** release 2.4.33.0 de 15/09/2026 e descrição da biblioteca.
- **Changelog:** 2.4.33.0 remove Sound Overrides; 2.4.32.0 registra sua introdução; mudanças 2.4.31.0 citadas acima permanecem baseline histórica.
- **Limite:** consumers físicos não foram inferidos somente pela autoria; devem ser confirmados por dependency metadata quando necessário.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
