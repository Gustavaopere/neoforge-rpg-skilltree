# Create: Blaze Burner Fuels

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81339839e798a1689808
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Blaze Burner Fuels
- **Arquivo JAR:** `create_blaze_burner_fuels-1.0.2-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 1.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Adiciona combustíveis alternativos para Blaze Burners, como pellets, coke, peat, briquetes e outras rotas de aquecimento/superheating.
- **Dependências:** Create 6.0.10 físico. O addon estende combustíveis do Blaze Burner/Steam Boiler; não substitui lava/coal nem cria uma segunda autoridade de heat state.
- **Sobreposição:** Amplia especificamente combustível de Blaze Burner/boiler. Cadeias de coke/peat de outros mods podem fornecer materiais semelhantes, mas a aceitação/tempo deste addon continua sendo sua superfície própria.
- **Compatibilidade/Riscos:** Riscos: fuel-time imbalance, superheat concedido indevidamente, recipe/tag loops, fuel duplication, boiler throughput inflation e overlap com outras cadeias de coke/peat. Burn times publicados são provider data e não devem ser recalculados por outro mod.
- **Observações:** JAR físico `create_blaze_burner_fuels-1.0.2-neoforge-1.21.1.jar`, mod id `create_blaze_burner_fuels`, runtime 1.0.2. Release NeoForge 1.21.1 de 11/01/2026, Client & Server.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create: Blaze Burner Fuels 1.0.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-blaze-burner-fuels
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; fuel catalog, burn/superheat authority, boiler economy, tags/recipes, balance risks e tests catalogados para 1.0.2.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🔥 **ESCOPO CANÔNICO.** Runtime físico 1.0.2. Blaze Burner Fuels amplia a lista/economia de combustíveis reconhecidos por Blaze Burners e, por consequência, opções de alimentação de Steam Boilers Create.

## 1. Authority de heat
Create continua owner dos estados de Blaze Burner e da exigência de heated/superheated recipes. O addon define quais combustíveis adicionais alimentam esses estados e por quanto tempo. Não deve existir uma segunda máquina de heat paralela.

## 2. Combustíveis próprios publicados
O catálogo oficial inclui, entre outros:
- Kindling Sticks — 400 ticks;
- Wood Pellets — 600;
- Fiery Coal e Fiery Charcoal — 4800;
- Coal/Charcoal Briquettes — 1000;
- Blazing Rocks — 3600;
- Coal Coke — 2400;
- Refined Coal Coke — 3200;
- Peat e Peat Briquettes, com briquette a 3200;
- Dung Brick — 800;
- Blaze Core — 8200;
- Superheated Blaze Core — 8200 e capacidade de superheating.

Wood Dust, Compost, Inactive Blaze Core e materiais associados compõem as cadeias de produção. Esses valores são dados publicados do provider; integrations não devem duplicá-los em lógica própria.

## 3. Blocos e vanilla fuels
O projeto também publica Stick Bundle, Makeshift Trapdoor e família de Peat Bricks. Entre fuels vanilla adicionais estão Hay Bale, Wheat, Gunpowder, Blaze Powder, Fire Charge, Paper e várias formas de books, com burn times próprios.
O Dung Brick possui interação de arremesso/easter egg; isso é conteúdo secundário e não deve ser confundido com fuel processing.

## 4. Superheating e balanceamento
Superheated Blaze Core é a superfície mais sensível: conceder superheating altera recipes avançadas Create. Qualquer datapack/KubeJS que também mexa na mesma fuel tag deve evitar conceder superheat duas vezes ou tornar um combustível barato equivalente ao caminho de progressão pretendido.

## 5. Steam Boiler economy
O projeto se posiciona como alternativa renovável/variada para construção de boilers, sem eliminar lava/coal. O impacto real depende do custo das rotas de coke, peat, pellets e cores no pack. TFMG e outros mods industriais podem fornecer materiais nominalmente semelhantes; validar item/tag exato antes de assumir interoperabilidade.

## 6. Data, recipes e reload
Fuel acceptance e crafting recipes devem ser tratados como data/provider state. Após `/reload`, viewer, burner e recipes precisam convergir. Não manter cache de burn time de item que deixou de ser aceito.

## 7. Client/server e multiplayer
Flame animation/HUD são feedback; consumo de combustível, remaining burn time e superheat são server-authoritative. Inserções simultâneas por automação devem consumir exatamente o stack esperado.

## 8. Riscos
1. Fuel barato recebe tempo excessivo e quebra economia.
2. Superheat é concedido por item/tag incorreto.
3. Duas integrações registram o mesmo combustível com valores divergentes.
4. Automação consome/duplica fuel sob lag.
5. Boiler throughput aumenta além do balance esperado.
6. `/reload` deixa burn-time stale.
7. Coke/peat de outro provider é aceito por tag ampla sem intenção.

## 9. Matriz de testes
- [ ] Dedicated server inicia com 1.0.2 + Create 6.0.10.
- [ ] Amostra de pellets/briquettes/coke respeita burn times publicados.
- [ ] Blaze Core dura conforme provider e não superaquece indevidamente.
- [ ] Superheated Blaze Core produz superheat e somente ele/itens autorizados o fazem.
- [ ] Automação insere combustível sem dupe/loss.
- [ ] Boiler mantém consumo proporcional sob carga.
- [ ] `/reload` atualiza fuel/recipe state de forma idempotente.
- [ ] Tags de coke/peat não capturam itens semanticamente errados.
Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
A página oficial 1.0.2 sustenta a lista de fuels, burn times e blocos acima. Esta ficha não afirma que materiais homônimos de outros mods sejam automaticamente intercambiáveis; essa equivalência depende de registry/tag runtime.