# Lootr

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8176b7b0d72cee081e21
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Lootr
- **Arquivo JAR:** `lootr-neoforge-1.21.1-1.11.38.125.jar`
- **Versão 1.21.1:** 1.21.1-1.11.38.125
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Exploração
- **Função:** Instancia e persiste loot de containers por jogador/UUID, com regras de conversão, refresh/decay, filtros por dimensão/loot table e controles específicos de multiplayer.
- **Dependências:** Minecraft 1.21.1 e NeoForge. A build analisada é autossuficiente quanto ao domínio Lootr; integrações com estruturas/loot tables são configuráveis e não equivalem a dependências obrigatórias de outros mods do pack.
- **Sobreposição:** Complementar a LootJS/Loot Integrations. Esses mods alteram conteúdo/condições de loot tables; Lootr controla conversão de containers e inventários individualizados por jogador.
- **Compatibilidade/Riscos:** Em multiplayer, cada UUID pode receber inventário próprio do mesmo container, multiplicando a oferta econômica de loot raro. SavedData por container/jogador pode crescer em servidores grandes; testar containers modded, blacklists, piecewise check, fake players e interação com LootJS/Loot Integrations.
- **Observações:** A branch mdg-1.21.1 já avançou para 1.11.38.126; o dossiê foi pinado à release 1.11.38.125 para evitar projeção de código posterior. Lootr não substitui LootJS/Loot Integrations: ele controla instância/acesso/persistência do loot por jogador.
- **Procedência:** Modlist física + release oficial CurseForge + source oficial LootrMinecraft/Lootr pinado ao commit 3289dfcb18ee65aaf871e322fc980cf8f60ada53 (1.11.38.125).
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lootr
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source pinado ao commit da release 1.11.38.125; persistência, configs, multiplayer, riscos e testes documentados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 📦 **Escopo canônico desta ficha:** `lootr-neoforge-1.21.1-1.11.38.125.jar`, exatamente o JAR presente na modlist física. A análise de código foi pinada ao commit de release `3289dfcb18ee65aaf871e322fc980cf8f60ada53`, cujo `gradle.properties` declara `mod_version=1.11.38.125`, Minecraft 1.21.1 e `mod_id=lootr`. A branch 1.21.1 já avançou para 1.11.38.126; portanto, mudanças posteriores não são usadas para redefinir esta ficha.

## 1. Identidade, versão e autoridade
- **Mod:** Lootr.
- **JAR no pack:** `lootr-neoforge-1.21.1-1.11.38.125.jar`.
- **Versão física:** `1.21.1-1.11.38.125`.
- **Loader:** NeoForge; a release correspondente foi publicada para Minecraft 1.21.1.
- **Mod ID:** `lootr`.
- **Upstream:** `LootrMinecraft/Lootr`.
- **Commit pinado:** `3289dfcb18ee65aaf871e322fc980cf8f60ada53` — release 1.11.38.125.
- A modlist física é a autoridade de presença e versão; CurseForge e o source pinado são usados para explicar comportamento.

## 2. Papel funcional no pack
Lootr altera a **semântica de acesso a containers de loot de estruturas**, criando inventários de loot separados por jogador. O próprio metadata do projeto resume o objetivo como gerar loot único para cada jogador que abre um container.
Isso significa que Lootr não é a camada que decide primariamente **quais itens** entram em uma loot table. Seu domínio é a instância/acesso/persistência do container por jogador. No pack, isso o diferencia dos módulos Loot Integrations/LootJS, que atuam sobre composição e transformação de loot tables.

## 3. Modelo de dados e persistência
O source 1.11.38.125 confirma `LootrSavedData extends SavedData` como parte central da persistência.
Por container/dado associado, `LootrSavedData` mantém:
- `Map<UUID, LootrInventory> inventories`: inventário separado por UUID de jogador;
- `Set<UUID> openers`: jogadores registrados como abridores visuais;
- `Set<UUID> actualOpeners`: abridores efetivos;
- `boolean hasBeenOpened`;
- `ILootrInfo info`, com os dados canônicos do container/redirecionamento.
Na serialização NBT, a implementação grava `inventories`, cada entrada com `uuid` e conteúdo `chest`, além de `openers`, `actualOpeners` e `hasBeenOpened`. Em reload, esses dados são reconstruídos. Portanto, o estado individualizado não é apenas visual ou transitório: ele pertence ao save.

## 4. Geração do inventário individual
Quando um jogador autorizado abre um provider sem inventário já criado, `createInventory(...)`:
1. verifica `provider.canPlayerOpen(player)`;
2. cria um `LootrInventory` a partir do inventário inicial do provider;
3. para jogador real, chama o preenchimento da loot table;
4. armazena o resultado em `inventories` usando o UUID do jogador;
5. marca `hasBeenOpened=true` e o `SavedData` como dirty.
A existência do mapa por UUID é a razão técnica de uma mesma estrutura poder render recompensas independentes para jogadores diferentes.

## 5. Refresh, limpeza e ciclo de vida
- `refresh()` limpa todos os inventários individualizados, redefine `hasBeenOpened=false` e marca os dados como alterados.
- `clearInventories(UUID)` remove apenas o inventário de um jogador e também remove seu estado de abertura visual correspondente.
- `canBeCulled()` só permite descarte quando não há inventários e o container não foi aberto.
- O source contém mecanismos de dados temporizados/decay/refresh e utilitários para limpeza de SavedData; isso é relevante para servidores grandes porque o volume de containers e jogadores pode produzir muitos registros persistentes.

## 6. Configuração NeoForge confirmada em 1.11.38.125
A classe `neoforge/.../ConfigManager.java` da release expõe configuração comum e client-side.

### Conversão e armazenamento
- `conversion.disable=false`: desativa globalmente conversão quando habilitado.
- `save_mode=SMART`: a própria descrição informa modos `always` e `when_opened`; SMART usa `always` normalmente, mas troca para `when_opened` em Aternos/exaroton.
- `randomise_seed=true`: controla se o seed usado para loot é randomizado por jogador.
- `max_age`: default `60 * 20 * 15` ticks (15 minutos) para idade máxima de entradas temporárias.
- `bypass_spawn_protection=true`.
- `convert_mineshafts=true` (campo marcado deprecated no código).
- `convert_elytras_to_chests=false`.
- `convert_elytras_to_item_frames=true`.
- `convert_item_frames=true`.
- `perform_piecewise_check=true`: descrição do próprio config alerta que é mais preciso, porém pode causar lag.
- `check_world_border=false`; a descrição recomenda restart do cliente após mudança de world border quando essa opção é usada.
- `tick_delay=20`.

### Filtros de escopo
Há whitelist/blacklist para:
- dimensões;
- mod IDs de dimensões;
- loot tables;
- mod IDs proprietários de loot tables;
- uma lista específica de `problematic_loot_tables`.
Esses filtros são o primeiro ponto de diagnóstico quando um container modded é convertido indevidamente ou deixa de ser convertido.

### Quebra e explosões
O config possui controles separados para bloqueio/permissão de quebra, fake players, resistência/imunidade a explosões, suporte próprio de brushables/item frames e drop do loot individual do jogador ao quebrar. Os defaults confirmados na release são conservadores: `disable_break=false`, `enable_break=false`, `enable_fake_player_break=false`, `blast_resistant=false`, `blast_immune=false`, `brushables_self_support=false`, `item_frames_self_support=false` e `should_drop_player_loot=false`.

### Redstone e mensagens
- `power_comparators=true`: comparadores em containers Lootr fornecem sinal conforme a regra própria descrita no config.
- `trapped_custom=false`.
- Existem controles de notificações, estilos de mensagem, decay e refresh por mod/loot table/dimensão.

### Cliente
O config separa opções client-only para texturas vanilla, novas texturas e partículas de container não aberto (`UNOPENED_PARTICLES`).

## 7. Multiplayer e economia
Lootr é especialmente relevante em multiplayer. Como os inventários são indexados por UUID, dois jogadores podem obter loot próprio a partir do mesmo ponto estrutural. Isso melhora justiça de exploração, mas **multiplica a oferta econômica potencial** de loot raro conforme o número de jogadores que visitam a estrutura.
Para balanceamento do pack, qualquer item de progressão inserido por LootJS/Loot Integrations deve ser avaliado considerando essa multiplicação por jogador, não apenas a frequência da loot table original.

## 8. Integrações e fronteiras de autoridade
- **Loot Integrations / LootJS:** alteram conteúdo/condições de loot; Lootr instancia e persiste acesso individual. São camadas complementares, não substitutas.
- **Estruturas modded:** Lootr depende de reconhecer/convertir containers e loot tables; as blacklists e `problematic_loot_tables` existem justamente para exceções.
- **Fake players / automação:** há regra própria para quebra por fake player e o código evita preencher loot de jogador real no mesmo caminho para fake player; automações que interagem com containers devem ser testadas.
- **Spawn protection/world border:** possuem tratamento configurável e podem mudar o resultado de acesso/conversão.

## 9. Mudanças específicas da release 1.11.38.125
O changelog oficial dessa build inclui:
- advancement para conversão de emergência;
- correção do caso em que `suspicious` bloqueava abertura do chest (#889);
- correção para não sobrescrever limiter;
- ajustes de frustum/linha de visão para partículas;
- bump final da versão para release.
Essas mudanças pertencem à build instalada e devem ser mantidas como referência quando se comparar comportamento com versões anteriores.

## 10. Riscos operacionais
1. **Economia multiplayer:** loot raro configurado em uma tabela pode ser obtido uma vez por jogador.
2. **Crescimento de save:** persistência por container e UUID aumenta dados conforme exploração/população; `save_mode`, culling, refresh/decay e limpeza precisam ser considerados em servidores grandes.
3. **Containers não convencionais:** estruturas/mods que usam containers, item frames ou lógicas especiais podem requerer blacklist/exception.
4. **Performance:** `perform_piecewise_check` explicitamente troca custo por precisão; `tick_delay` e rotinas temporizadas merecem observação em pack grande.
5. **Automação/fake players:** não presumir comportamento equivalente a jogador real.
6. **Partículas/client:** problemas puramente visuais devem ser separados de falhas de geração/persistência server-side.

## 11. Matriz mínima de testes para este pack
- **SP:** abrir um chest Lootr, relogar e confirmar persistência do inventário individual.
- **MP, 2 jogadores:** abrir o mesmo container com A e B; confirmar inventários independentes e contabilizar impacto econômico.
- **LootJS/Loot Integrations:** usar uma estrutura cuja tabela foi modificada e verificar se cada jogador recebe a versão modificada da tabela.
- **Restart do servidor:** reabrir containers já utilizados e validar NBT/persistência.
- **Refresh/decay:** quando configurados, validar tempo, mensagem e reconstrução/remoção esperada.
- **Estrutura modded problemática:** testar container fora do padrão e, se necessário, registrar a loot table em blacklist/problematic list.
- **Fake player/automação:** testar apenas quando algum sistema do pack realmente interagir com Lootr dessa forma.
- **Performance:** observar criação massiva de containers em exploração de chunks e tamanho/quantidade de dados persistidos.

## 12. Evidências consultadas
- Modlist física do projeto: `lootr-neoforge-1.21.1-1.11.38.125.jar`.
- CurseForge oficial: https://www.curseforge.com/minecraft/mc-mods/lootr — release 1.11.38.125 para MC 1.21.1.
- Source oficial: https://github.com/LootrMinecraft/Lootr
- Commit pinado: https://github.com/LootrMinecraft/Lootr/commit/3289dfcb18ee65aaf871e322fc980cf8f60ada53
- `gradle.properties` da release: versão 1.11.38.125 / MC 1.21.1.
- `LootrSavedData.java` da release: persistência NBT e inventários por UUID.
- `ConfigManager.java` da release: parâmetros NeoForge descritos acima.

> ⚠️ **Limite de evidência:** a branch `mdg-1.21.1` já está em 1.11.38.126. Esta ficha evita atribuir à 1.11.38.125 mudanças posteriores e usa o commit de release como authority de código.
