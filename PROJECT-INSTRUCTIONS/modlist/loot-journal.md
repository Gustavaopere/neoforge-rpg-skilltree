# Loot Journal

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811cb931d77f7a62170c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Loot Journal
- **Arquivo JAR:** `loot_journal-neoforge-1.21.1-6.2.1.jar`
- **Versão 1.21.1:** 6.2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Visual
- **Função:** HUD client-side de notificações de itens coletados, com animações, smart stacking, filtros/customização e visualização de pickups próprios ou próximos sem alterar a geração ou posse do loot.
- **Dependências:** Obrigatórias publicadas: Fragmentum [NeoForge Edition] + YetAnotherConfigLib. Runtime físico contém Fragmentum 2.4.4 e YACL 3.8.2+1.21.1-neoforge.
- **Sobreposição:** Sobrepõe apenas apresentação de pickups/HUD. Não injeta loot, não altera loot tables e não deve ser tratado como authority de inventário; geração/ownership permanecem no servidor e nos mods de loot.
- **Compatibilidade/Riscos:** Client-side. Riscos: HUD/render overhead em pickup massivo, stacking visual divergente do inventário, filtros/config stale, overlap com outros HUDs, YACL/Fragmentum drift e exposição visual de pickups de jogadores próximos. Source atual já avançou para MC 1.21.11; não usar internals modernos como prova da build 1.21.1.
- **Observações:** Release exata `loot_journal-neoforge-1.21.1-6.2.1.jar`, publicada em 25/05/2026; changelog da build: port para Minecraft 1.21.1. Projeto oficial classifica o mod como Client.
- **Procedência:** modlist.txt física atual + release oficial Loot Journal 6.2.1 NeoForge 1.21.1 + dependências oficiais Fragmentum/YACL + repositório ObscuriaLithium/loot-journal usado apenas para escopo/README, com source drift explícito para Minecraft mais novo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/loot-journal
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 6.2.1 reconciliada; HUD/pickup observation, smart stacking, config/dependencies, client-only boundary, source drift, performance/privacy risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 📦 **ESCOPO CANÔNICO.** Runtime físico: `loot_journal-neoforge-1.21.1-6.2.1.jar`, mod id `loot_journal`, versão `6.2.1`. Loot Journal é um mod **client-side** de apresentação de pickups; não cria loot, não altera loot tables e não é autoridade sobre inventário ou ownership de itens.

## 1. Identidade e versão
- **Mod:** Loot Journal: Pickup Notifier [NeoForge Edition].
- **JAR:** `loot_journal-neoforge-1.21.1-6.2.1.jar`.
- **Versão instalada:** `6.2.1`.
- **Minecraft/loader:** 1.21.1 / NeoForge.
- **Ambiente publicado:** Client.
- **Release exata:** 25/05/2026; changelog da build 1.21.1: **Ported to Minecraft 1.21.1**.
- **Source drift:** o repositório público atual já avançou para Minecraft 1.21.11; internals dessa linha não são usados como prova da build física 1.21.1.

## 2. Papel no modpack
O mod observa eventos de coleta e apresenta notificações animadas na HUD. O projeto oficial descreve **smooth animated notifications**, **smart stacking**, **full visual customization**, acompanhamento de pickups próprios e de jogadores próximos e configuração visual em jogo. Seu papel é tornar aquisição de itens legível sem mudar a origem, quantidade ou posse real do item.

## 3. Authority / ownership
- **Servidor e providers de loot:** continuam autoridade sobre geração de drops, loot tables, inventários, containers e entrega efetiva de itens.
- **Loot Journal:** autoridade apenas sobre sua camada visual local: layout, animação, agrupamento visual, filtros e apresentação.
- **Loot Integrations/LootJS/datapacks:** podem mudar o conteúdo gerado, mas Loot Journal apenas observa o resultado recebido pelo cliente.
- **Lootr:** controla instanciamento de loot por jogador em containers; Loot Journal não substitui esse comportamento.

## 4. Conteúdo e registries
Esta auditoria não atribui blocos, mobs, armas, loot tables ou worldgen próprios ao Loot Journal. A função confirmada é HUD/QoL client-side. Não foram inventados registry IDs internos porque o source público exato da build 1.21.1 não foi pinado.

## 5. Pipeline funcional
1. Um item é coletado/observado no runtime.
2. O mod produz uma notificação visual local.
3. Pickups compatíveis podem ser agregados por **smart stacking** para reduzir spam visual.
4. Filtros e opções de tema/layout determinam o que aparece e como aparece.
5. O estado visual deve desaparecer/atualizar sem alterar o inventário real.
Esse pipeline é de apresentação: divergência de HUD não pode ser tratada automaticamente como dupe/loss de item.

## 6. Pickups próprios e próximos
O README oficial informa que o mod acompanha pickups do próprio jogador e de jogadores próximos em tempo real. Isso aumenta informação situacional em multiplayer, mas também cria uma superfície de **privacidade/telemetria visual local**: itens coletados por terceiros podem ser perceptíveis quando o jogo/mod fornece informação suficiente ao cliente.

## 7. Smart stacking
O agrupamento reduz múltiplas notificações visualmente equivalentes. O stacking é **visual**; a quantidade real continua sendo determinada pelo inventário/evento observado. Testes devem comparar contador exibido versus quantidade efetivamente recebida, especialmente em pickups rápidos, stacks parciais e itens com componentes diferentes.

## 8. Configuração e dependências
A distribuição 6.2.1 usa **YetAnotherConfigLib (YACL)** para configuração e **Fragmentum** como dependência publicada do ecossistema. No runtime físico do pack estão presentes YACL `3.8.2+1.21.1-neoforge` e Fragmentum `2.4.4`. A ficha não projeta nomes de opções internos da linha 1.21.11 sobre a build 1.21.1 sem source pin correspondente.

## 9. Client / Server boundary
Loot Journal é publicado como **Client**. Dedicated server não deve depender dele para gerar ou validar gameplay. Qualquer crash de classloading/render deve ser investigado como boundary client-side; a ausência do mod no servidor não deve apagar, duplicar ou impedir loot server-authoritative.

## 10. Lifecycle
Validar especificamente:
- entrada/saída de mundo;
- relog;
- troca de dimensão;
- morte/respawn;
- resource reload;
- mudança de GUI/HUD scale;
- reconexão multiplayer;
- bursts de pickups durante chunk load ou combate.
Notificações antigas não devem reaparecer indevidamente depois de reconexão ou reload.

## 11. Multiplayer
O mod não deve transformar observação local em autoridade sobre outro jogador. Em sessões com muitos jogadores próximos, validar volume de notificações, identificação visual correta e ausência de duplicação ao receber múltiplos eventos semelhantes. Qualquer diferença entre cliente e servidor deve ser resolvida em favor do state do servidor/inventário.

## 12. Performance e apresentação
Burst de drops pode gerar muitas entradas, animações e operações de filtro/stacking. O risco principal é **overhead de HUD/render**, não custo de worldgen ou AI. Testar farms, mineração em massa, loot de bosses e abertura/coleta de containers com dezenas de itens.

## 13. Compatibilidade com o pack
- **Loot Integrations / addons:** aumentam ou diversificam loot; Loot Journal apenas apresenta o pickup final.
- **Lootr:** pode produzir recompensas individualizadas por jogador; observar se notificações continuam correspondendo ao inventário local.
- **JEI/Advanced Loot Info:** são superfícies de consulta, não providers do evento de pickup.
- **Outros HUD mods:** podem disputar espaço de tela, escala, layering ou legibilidade.
Nenhuma integração de código específica com esses mods foi presumida sem evidência.

## 14. Riscos técnicos
1. **Visual stacking mismatch:** contador visual não refletir exatamente stacks/componentes recebidos.
2. **Duplicate notification:** um mesmo pickup aparecer mais de uma vez por caminhos de observação distintos.
3. **Stale HUD state:** entradas sobreviverem a world/relog/dimension lifecycle incorretamente.
4. **Render overhead:** bursts de pickups degradarem FPS ou frame time.
5. **HUD overlap:** conflito visual com outros overlays.
6. **Dependency drift:** YACL/Fragmentum incompatíveis com a build do mod.
7. **Source drift:** usar código 1.21.11 como se fosse prova da 1.21.1.
8. **Information exposure:** pickups de jogadores próximos serem mostrados além do desejado para a experiência do servidor.

## 15. Matriz de testes
- [ ] Client inicia com Loot Journal 6.2.1, Fragmentum e YACL físicos.
- [ ] Dedicated server inicia sem exigir Loot Journal como provider de gameplay.
- [ ] Pickup simples gera exatamente uma notificação coerente.
- [ ] Vários itens iguais em sequência agregam sem alterar a quantidade real.
- [ ] Itens iguais com componentes/NBT/data components diferentes não são confundidos visualmente de forma enganosa.
- [ ] Bursts de dezenas/centenas de pickups não causam degradação severa de HUD/render.
- [ ] Relog, death/respawn e dimension change limpam estado visual antigo.
- [ ] Resource reload e mudança de escala preservam layout sem crash.
- [ ] Multiplayer com pickups próximos não duplica eventos do próprio jogador.
- [ ] Loot recebido via Lootr/Loot Integrations continua correspondendo ao inventário server-authoritative.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
Evidências usadas: modlist física atual; release oficial CurseForge `6.2.1` para NeoForge 1.21.1; changelog da build; ambiente publicado **Client**; página/README oficial do projeto; dependências publicadas e runtime físico de Fragmentum/YACL. O repositório atual está em Minecraft 1.21.11, portanto classes/config internals dessa linha não foram transplantados para esta ficha como se fossem da build 1.21.1.
