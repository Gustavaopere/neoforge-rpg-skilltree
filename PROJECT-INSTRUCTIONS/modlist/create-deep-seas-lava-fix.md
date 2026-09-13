# Create: Deep Seas - Lava Fix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81728888efc6708ca89e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Create: Deep Seas - Lava Fix
- **Arquivo JAR:** `submarinefix-1.0.1.jar`
- **Versão 1.21.1:** 1.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Hotfix player-only para Create: Deep Seas: detecta compartimento selado e suprime dano/fire ticks/overlay de LAVA, IN_FIRE, ON_FIRE e HOT_FLOOR dentro de submarinos submersos em lava.
- **Dependências:** Create: Deep Seas é alvo funcional e o mod no-opa sem ele. O fix foi desenvolvido contra Deep Seas 2.1.6 + Sable Companion 1.5.0; runtime físico atual usa Deep Seas 2.2.4, portanto há version drift a revalidar.
- **Sobreposição:** Não adiciona submarinos; corrige comportamento do addon alvo. Pode tornar-se obsoleto se Deep Seas incorporar a correção.
- **Compatibilidade/Riscos:** Riscos: drift de CompartmentTracker/transforms em Deep Seas 2.2.4, over/under-protection em hatches/ladders/seats, client/server mismatch e obsolescência se CDS incorporar fix nativo. Limites upstream: mobs continuam vulneráveis, lava surface/lighting e pressure-in-lava não são corrigidos.
- **Observações:** mod id `submarinefix`; runtime 1.0.1; player-only. Decisão Sem decisão preservada. O mod pode carregar sem Deep Seas, mas não tem função nesse caso. Reavaliar necessidade se Deep Seas integrar a correção nativamente.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/source oficial Create: Deep Seas - Lava Fix 1.0.1 + runtime físico Create: Deep Seas 2.2.4. Dossiê técnico de 08/09 preservado; compatibilidade runtime com CDS 2.2.4 segue como teste pendente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas-lava-fix ; https://github.com/Diyksfol/create-deep-seas-lava-fix
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Create: Deep Seas - Lava Fix 1.0.1 permanece exatamente instalado; hotfix player-only, CompartmentTracker/transforms, drift CDS 2.1.6→2.2.4, limites, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `submarinefix-1.0.1.jar`, mod id `submarinefix`, runtime name `Create: Deep Seas - Lava Fix`, versão `1.0.1`. É um **hotfix específico para jogadores** dentro de compartimentos selados de Create: Deep Seas submersos em lava; não corrige toda a simulação/renderização de lava do addon.

## 1. Identidade, versão e decisão
- **Mod:** Create: Deep Seas - Lava Fix.
- **JAR físico:** `submarinefix-1.0.1.jar`.
- **Mod id:** `submarinefix`.
- **Versão:** `1.0.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Decisão:** Sem decisão; preservada.

## 2. Alvo e version drift
O fix foi desenvolvido/compilado usando como referência:
- Create: Deep Seas `2.1.6`;
- Sable Companion `1.5.0` bundled naquela versão.
O pack atual contém **Create: Deep Seas 2.2.4**, portanto existe **version drift real** entre a referência de desenvolvimento e o runtime físico. A presença do JAR não prova compatibilidade plena com 2.2.4; isso é regression gate obrigatório.

## 3. Authority e ownership
- **Create: Deep Seas:** authority dos submarinos, compartimentos selados, pumping/sealing, sublevels e mecânica de pressão.
- **Sable:** authority da infraestrutura física/sublevel usada pelo Deep Seas.
- **Lava Fix:** apenas reconhece quando o jogador está dentro de compartimento selado e suprime determinados efeitos vanilla de fogo/lava que o CDS não estava interceptando.
O fix não deve substituir a lógica de sealing/pressure do Deep Seas nem decidir se um compartimento é válido fora das APIs/estruturas lidas do addon.

## 4. Problema corrigido
Em submarino hermeticamente selado, o Deep Seas consegue remover o fluido do interior, mas o código vanilla ainda pode considerar o jogador exposto a lava/fire devido à posição física/sublevel.
O hotfix protege o jogador quando está:
- em interior selado submerso em lava;
- em hatch/doorway no nível da superfície durante entrada/saída;
- sentado em Create seats;
- em ladders/shafts;
- em doorways internos.
Também corrige fire ticks residuais e o overlay de fogo persistente no cliente.

## 5. Camadas técnicas de proteção
O README/source oficial descreve múltiplas camadas:
### Server — incoming damage
`LivingIncomingDamageEvent` cancela damage sources:
- `LAVA`;
- `IN_FIRE`;
- `ON_FIRE`;
- `HOT_FLOOR`;
quando o jogador é detectado dentro de compartimento selado.
### Server — base tick
Mixin em `Entity#baseTick` suprime chamada a `lavaHurt()` para o player protegido.
### Client — local base tick
Mixin em `LocalPlayer#baseTick` suprime `lavaHurt()` client-side, evitando geração local de fire ticks.
### Client — fire overlay
Mixin em `ScreenEffectRenderer#renderFire` impede o overlay de chamas quando a condição de proteção é satisfeita.
### Server — residual fire
`PlayerTickEvent.Post` limpa fire ticks residuais enquanto o player permanece no contexto protegido.
A redundância é deliberada para cobrir dano, ticking e rendering em ambos os lados.

## 6. Detecção de compartimento selado
O algoritmo publicado:
1. itera submarinos por `CompartmentTracker.getSubsSnapshot()`;
2. rejeita submarinos cujo `getWorldAABB` não contém o jogador;
3. transforma world position em coordenadas plot-local via `subLevelAccess.logicalPose().transformPositionInverse(...)`;
4. compara posição contra `internal()` e `hull()` dos `Component`s selados obtidos por `CompartmentTracker.getCompartments(uuid)`;
5. verifica **feet e eye positions**;
6. se o jogador está climbable, faz scan vertical adicional para cobrir ladder shafts acima do hull.
Esse detalhamento é importante porque seats, ladders e doorways são justamente os edge cases de detecção espacial.

## 7. O que o fix NÃO cobre
O upstream explicitamente mantém fora do escopo:
- **lava surface texture** ainda visível dentro do submarino;
- trapdoors que não funcionam como ladder submersa devido à mecânica de fluid do CDS;
- fire colocado manualmente no interior com comportamento estranho/fluid-like;
- lava/água despejada diretamente dentro do sub ser tratada segundo regras internas do CDS;
- **mobs/animals continuam tomando dano de fogo/lava**;
- objetos animados/itens/gears podem ficar fortemente avermelhados por lighting do sublevel;
- **pressure system** do Deep Seas não passa a funcionar em lava por causa deste fix.
Esses limites devem permanecer registrados para não transformar ausência de suporte em bug deste hotfix.

## 8. Dependência opcional/no-op
O projeto declara Create: Deep Seas como dependência opcional no sentido de load: sem CDS, o mod simplesmente não faz nada.
Isso não significa utilidade autônoma. No pack, sua razão de existir é exclusivamente o Deep Seas.

## 9. Client / server e multiplayer
- Servidor: authority do dano, fire ticks e state do player.
- Cliente: overlay e client fire state precisam espelhar a proteção do servidor.
- Multiplayer remoto deve testar que proteção não fica apenas no host/integrated server.
- Cliente não pode esconder overlay enquanto servidor ainda aplica dano, nem servidor cancelar dano deixando fire state residual visual indefinidamente.

## 10. Lifecycle
Validar:
- entrar em sub selado fora da lava;
- submergir progressivamente em lava;
- abrir/fechar hatch;
- climb em ladder durante passagem pela superfície;
- sentar/levantar de Create seat;
- breach do hull → receber dano normal;
- reseal → proteção volta sem endless fire ticks;
- chunk unload/reload;
- disassembly/reassembly do submarino;
- server restart enquanto sub está em lava;
- morte/respawn/relog dentro/próximo do sub.

## 11. Integrações concretas no pack
- **Create: Deep Seas 2.2.4:** alvo direto; versão mais nova que a referência de desenvolvimento 2.1.6, teste obrigatório.
- **Sable:** transforms/sublevels usados pela detecção.
- **Create seats:** edge case explicitamente coberto pelo upstream.
- Mods que alterem fire immunity/damage events podem interceptar os mesmos damage sources; ordem/event priority precisa de regressão se houver comportamento anômalo.
- Shaders/Iris podem alterar aparência da lava/lighting, mas não o cancelamento server-side do dano.

## 12. Riscos técnicos
1. **Version drift CDS 2.1.6→2.2.4:** APIs/fields de CompartmentTracker ou transforms podem mudar.
2. **Over-protection:** detector amplo demais poderia cancelar lava/fire fora de sub selado.
3. **Under-protection:** doorways/ladders/seats são edge cases espaciais complexos.
4. **Client/server mismatch:** overlay escondido sem proteção real ou proteção real com visual residual.
5. **Reseal state:** breach/reseal pode deixar fire ticks stale.
6. **Entity scope:** proteção é player-only; mobs continuam vulneráveis por design.
7. **Obsolescência futura:** se Deep Seas incorporar fix nativo, este mod pode duplicar interceptação e deve ser reavaliado.

## 13. Matriz de testes
- [ ] Dedicated server boot com Deep Seas 2.2.4 + fix 1.0.1.
- [ ] Player em interior selado submerso não recebe LAVA/IN_FIRE/ON_FIRE/HOT_FLOOR.
- [ ] Player fora do sub continua recebendo dano normal.
- [ ] Hatch/doorway na superfície não aplica dano indevido.
- [ ] Ladder shaft coberto pela detecção vertical.
- [ ] Create seat dentro do sub mantém proteção.
- [ ] Breach remove proteção imediatamente.
- [ ] Reseal restaura proteção e limpa fire residual sem tornar player globalmente imune.
- [ ] Fire overlay some somente no contexto protegido.
- [ ] Mobs/animals continuam tomando dano conforme escopo upstream.
- [ ] Restart/relog/chunk unload não quebram detecção.
- [ ] Comparar comportamento com Deep Seas 2.2.4 sem o fix em cópia de teste.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 14. Evidências
- Modlist física canônica 08/09/2026: `submarinefix-1.0.1.jar` e Deep Seas 2.2.4.
- CurseForge oficial: release 1.0.1 NeoForge 1.21.1, Client & Server e problema corrigido.
- Repositório/README oficial: eventos/mixins, algoritmo `CompartmentTracker`, edge cases e limitações explícitas; build de referência usa Deep Seas 2.1.6 e Sable Companion 1.5.0.

## 15. Revalidação física — 11/09/2026
A modlist física atual mantém exatamente `submarinefix-1.0.1.jar`, mod id `submarinefix`, runtime `1.0.1`. Create: Deep Seas permanece no runtime físico `2.2.4`, mantendo o version drift já documentado em relação à referência de desenvolvimento 2.1.6 do hotfix.
A publicação oficial continua em 1.0.1 para NeoForge 1.21.1. Nenhum teste em submarino submerso em lava foi executado nesta recatalogação; proteção, breach/reseal, ladders/seats e client/server overlay permanecem integralmente pendentes.
