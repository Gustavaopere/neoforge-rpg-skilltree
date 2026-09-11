# Immersive Portal - Iron's Spells 'n Spellbooks Addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db8110b3b8db25d528f6a8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Immersive Portal - Iron's Spells 'n Spellbooks Addon
- **Arquivo JAR:** `immersive_portal_irons_spells_n_spellbooks_addon-1.0.1.jar`
- **Versão 1.21.1:** 1.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Compat
- **Função:** Bridge que substitui/adapta o Portal Spell de Iron's Spells para criar portais contínuos usando o core Immersive Portals fornecido pelo rewrite Immersive Aeronautics.
- **Dependências:** Obrigatórias funcionais: Iron's Spells 'n Spellbooks 3.16.3 + `immersive_portals_core` 6.0.7 fornecido por Immersive Aeronautics 1.1.4. Source público main ainda declara addon 1.0.0 e Iron's 3.16.2.
- **Sobreposição:** Não substitui Iron's nem Immersive Portals; cobre o Portal Spell entre ambos. True Immersion é addon distinto de interação através de portais e não é equivalente a esta bridge.
- **Compatibilidade/Riscos:** Bridge recente e mixin-based. Riscos: source drift 1.0.0→1.0.1, Portal Spell API drift, portal lifecycle/despawn, duplicate cast/portal creation, size config invalid, entity transfer e incompatibilidade de internals com o rewrite Immersive Aeronautics apesar do mesmo core 6.0.7.
- **Observações:** Release física 1.0.1 é oficial; changelog apenas diz `Fixed some issues`. Source público atual confirma build contra Immersive Portals 6.0.7 e Iron's 3.16.2, mas ainda reporta mod_version 1.0.0. Não inventar quais issues foram corrigidos na 1.0.1.
- **Procedência:** modlist.txt física atual + CurseForge oficial file 8770439, Release NeoForge 1.21.1 de 30/08/2026 + GitHub oficial AitherLight/... usado estruturalmente; source main está em mod_version 1.0.0, portanto não é byte/source pin da release física 1.0.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immersive-portal-irons-spells-n-spellbooks-addon/files/8770439
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Immersive Portal × Iron's Spells 1.0.1 release-pinned; Portal Spell bridge, configurable portal size, core 6.0.7/Iron's 3.16.3 matrix, source drift 1.0.0, lifecycle/multiplayer, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-06: a auditoria inicialmente não localizou a 1.0.1 porque o índice público ainda mostrava 1.0.0. Reconsulta posterior ao CurseForge atualizado confirmou 1.0.1 como release oficial atual (File ID 8770439); decisão fechada em Manter.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `immersive_portal_irons_spells_n_spellbooks_addon-1.0.1.jar`, mod id `immersive_portal_irons_spells_n_spellbooks_addon`, versão `1.0.1`. A release oficial é o CurseForge file 8770439 de 30/08/2026. O source público oficial ainda declara `mod_version=1.0.0`, então ele é usado apenas para arquitetura/dependencies; o comportamento version-specific permanece release-pinned.

## 1. Papel e authority
Este addon cria uma ponte entre o **Portal Spell de Iron's Spells 'n Spellbooks** e o runtime de Immersive Portals. Iron's continua authority do spell framework, mana/cast/cooldown; `immersive_portals_core` continua authority dos portais, linkage e entity transfer; a bridge controla somente a adaptação entre o resultado do spell e a criação do portal imersivo.

## 2. Matriz física atual
O pack contém Iron's Spells 3.16.3 e o core `immersive_portals_core` 6.0.7 fornecido pelo arquivo Immersive Aeronautics 1.1.4. A release 1.0.1 do addon está fisicamente presente. Essa é a matriz real a validar; não instalar outro Immersive Portals top-level, pois o rewrite já fornece o mod id requerido.

## 3. Source drift
O repositório oficial `AitherLight/Immersive-Portal---Iron-s-Spells-n-Spellbooks-Addon` confirma Minecraft 1.21.1, Immersive Portals 6.0.7 e a arquitetura de integração, mas o `gradle.properties` atual ainda declara **addon 1.0.0**, Iron's 3.16.2 e NeoForge 21.1.233. Portanto classes/internals do source podem informar estrutura, mas não são prova de byte-equivalence com a release física 1.0.1/Iron's 3.16.3.

## 4. Portal Spell
A função publicada é permitir que o Portal Spell de Iron's gere **immersive portals** em vez de usar um comportamento incompatível/padrão não integrado. Admission do cast, custo de mana, cooldown e sucesso/falha pertencem a Iron's; criação/linkage do portal pertence ao core. A bridge não deve liquidar custo ou criar portal duas vezes.

## 5. Tamanho configurável
O projeto declara que o **tamanho do portal é configurável**. Valores devem ser validados antes da criação para evitar geometria inválida, portal degenerado ou área exagerada. Sem schema/default exato pinado à 1.0.1, esta ficha não inventa nome de chave, range ou valor padrão.

## 6. Exactly-once cast → portal
O ponto crítico é a transação entre cast completion e portal spawn/link. Packet retry, lag, animation callback ou outro spell event não podem criar múltiplos pares de portais para um único cast autorizado. Se a criação falhar, custo/recovery devem seguir o contrato real do provider em vez de serem “compensados” por lógica externa improvisada.

## 7. Portal lifecycle
Após criação, validar existência dos dois lados/linkage, destino, duração/removal conforme runtime, chunk unload, dimension transfer e server restart. Uma metade removida/inválida não deve deixar referência órfã, teleport loop ou portal duplicado após reconnect.

## 8. Entity/player transfer
Travessia é authority do core Immersive Portals. A bridge não deve fazer teleport adicional ao detectar colisão no portal. Velocity/orientation e destination devem ser preservados conforme o core; qualquer gameplay effect do spell deve ocorrer uma única vez no contexto correto.

## 9. Relação com Immersive Aeronautics
Embora a dependency seja nominalmente Immersive Portals, o pack usa o **rewrite Immersive Aeronautics 1.1.4**, que expõe o mesmo `immersive_portals_core` 6.0.7. Compatibilidade de mod id/version permite carregamento, mas não garante que mixin/internals sejam idênticos ao upstream original. O smoke test deve ser feito especificamente contra o rewrite instalado.

## 10. Release 1.0.1
O changelog oficial da 1.0.1 informa apenas **“Fixed some issues.”** Sem detalhes adicionais publicados, não é permitido atribuir correções específicas a esta build. O dossier trata 1.0.1 como provenance exata, mas mantém os fixes concretos fail-closed.

## 11. Client / server
A distribuição é Client & Server. Servidor deve decidir cast success, mana/cooldown, portal creation/linkage e entity transfer. Cliente apresenta spell animation, portal rendering e preview visual. Um portal visível client-side não pode ser tratado como criado antes da confirmação server-side.

## 12. Lifecycle e multiplayer
Validar spell cast/cancel, creation failure, chunk unload, portal removal, player death/respawn, reconnect, dimension transfer e server restart. Dois jogadores castando simultaneamente precisam gerar pares independentes, sem compartilhar destination/state ou consumir mana do player errado.

## 13. Compatibilidade com True Immersion
`Immersive Portals: True Immersion 2.0.4` também está instalado, mas atua em interações através dos portais. Não é substituto desta bridge de spell. Os dois podem tocar portal interaction/runtime e precisam de smoke test conjunto para evitar mixin/order issues.

## 14. Riscos técnicos
- source 1.0.0 ser tratado como se fosse 1.0.1 exato;
- update de Iron's alterar Portal Spell/cast API;
- rewrite Immersive Aeronautics divergir de internals esperados pelo addon;
- cast gerar portal duas vezes;
- custo/cooldown liquidado em branch diferente do portal creation;
- size config produzir geometria inválida;
- portal half/link stale após unload/restart;
- entity receber teleport duplicado;
- client render ser usado como authority;
- True Immersion/outro addon mixinar o mesmo path de interação.

## 15. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com addon 1.0.1, Iron's 3.16.3 e core 6.0.7 do Immersive Aeronautics.
- [ ] Portal Spell válido cria exatamente um par/link de portal.
- [ ] Cast cancelado/falhado não deixa portal parcial.
- [ ] Mana/cooldown são liquidados exatamente uma vez.
- [ ] Config de tamanho aceita valores válidos e rejeita/normaliza inválidos conforme runtime.
- [ ] Player/entity atravessa sem double teleport.
- [ ] Chunk unload/reload não deixa metade stale.
- [ ] Reconnect/restart preserva/remove portal conforme lifecycle real.
- [ ] Dois jogadores castando simultaneamente mantêm state isolado.
- [ ] True Immersion 2.0.4 coexistindo não duplica interação/transfer.
- [ ] Update futuro de Iron's/core é bloqueado até regression test conjunto.

## 16. Evidências e limites
- **Modlist física:** JAR/runtime 1.0.1 e core 6.0.7 instalado via Immersive Aeronautics.
- **CurseForge oficial:** file 8770439, Release NeoForge 1.21.1, função Portal Spell → immersive portal e portal size configurável.
- **GitHub oficial:** arquitetura/dependencies, mas source atual ainda em addon 1.0.0; não é pin exato da 1.0.1.
- **Limite:** changelog 1.0.1 não especifica os fixes; config key/default/range e targets de mixin exatos permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
