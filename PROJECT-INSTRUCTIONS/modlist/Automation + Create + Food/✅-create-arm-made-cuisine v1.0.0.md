# Create: Arm-made Cuisine

## Propriedades do registro

- **Mod:** Create: Arm-made Cuisine
- **Arquivo JAR:** create_cuisine-1.0.0-mc1.21.1-neoforge.jar
- **Versão 1.21.1:** 1.0.0
- **Categoria:** Comida, Compat, Automação
- **Função:** Integra Mechanical Arms do Create com a Cuisine Skillet do Cuisine Delight, permitindo inserir ingredientes, mexer e retirar/pratar preparos de forma automatizada.
- **Dependências:** Create 6.0.10 + Cuisine Delight 1.2.10 estão fisicamente presentes. A release NeoForge 1.21.1 foi testada upstream contra NeoForge 21.1.206 + Create 6.0.8; o pack físico usa NeoForge 21.1.250 + Create 6.0.10, portanto smoke-test local permanece obrigatório.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: ingredient/package duplication, spatula stir loop, plate extraction duplicada, arm route ordering, skillet state stale e jar-in-jar/API drift. Exactly-once é requisito central para input, stir e output.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-arm-made-cuisine
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_cuisine` 1.0.0 + CurseForge oficial da build NeoForge 1.21.1 revalidado em 20/09/2026 + metadata jar-in-jar; comparação explícita com stack físico NeoForge 21.1.250 / Create 6.0.10.
- **Observações:** JAR físico `create_cuisine-1.0.0-mc1.21.1-neoforge.jar`, mod id `create_cuisine`, runtime 1.0.0. Release oficial NeoForge 1.21.1 de 17/01/2026, Client & Server. O JAR embarca L2Core/L2ModularBlocks/L2Serial internamente; não são top-levels. Upstream declara teste contra NeoForge 21.1.206 + Create 6.0.8.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — registro histórico do lote físico #138 na snapshot então vigente; posição física atual #139: create_cuisine-1.0.0-mc1.21.1-neoforge.jar / 1.0.0 confirmados; Mechanical Arm↔Cuisine Skillet exactly-once flow preservado e pin upstream NeoForge 21.1.206/Create 6.0.8 comparado ao stack físico atual 21.1.250/6.0.10.
- **Decisão:** Sem decisão
- **Sobreposição:** Bridge específica Mechanical Arm↔Cuisine Delight. Central Kitchen e outros addons culinários automatizam outras estações/recipes; não são equivalentes integrais.

> 🍳 **ESCOPO CANÔNICO.** Runtime físico `create_cuisine-1.0.0-mc1.21.1-neoforge.jar`. Arm-made Cuisine é uma bridge pequena e específica: permite que Mechanical Arms do Create operem a Cuisine Skillet do Cuisine Delight.

## 1. Authority
Cuisine Delight continua owner da Skillet, recipes, cooking progress e dish state. Create continua owner do Mechanical Arm, routing e interaction sequencing. O addon traduz ações entre os dois providers; não deve manter recipe ou inventory paralelo.
## 2. Fluxos oficiais
A documentação publica quatro comportamentos principais:
- adiciona Ponder para a Cuisine Skillet;
- Mechanical Arm pode inserir food na Skillet aquecida;
- pode tentar inserir packages que contenham food;
- segurando Spatula, o Arm mexe continuamente enquanto houver food;
- segurando Plate e com a rota de exits configurada, retira dishes e os entrega ao próximo exit.
Esses fluxos formam uma máquina de estados de input→stir→plate→delivery.
## 3. Input e packages
Inserir item direto ou package precisa consumir exatamente a quantidade aceita pela Skillet. Package container/remainder não pode ser duplicado nem perdido por retry do Arm. Se a Skillet rejeitar input, o Arm deve manter/retornar state coerente em vez de remover o stack client-side.
## 4. Spatula e stirring
O modo Spatula é contínuo enquanto houver food. A interação não deve criar loop infinito após a Skillet esvaziar, nem aumentar cooking progress duas vezes por tick por coexistir com interação manual. Cuisine Delight permanece authority de quando stirring é válido.
## 5. Plate e output
Com Plate, o Arm retira o dish concluído e segue para o próximo exit configurado. Output extraction precisa ser exactly-once: um dish final não pode existir simultaneamente na Skillet, mão do Arm e destino. Falha no destino deve preservar o item em um único owner conhecido.
## 6. Ponder e client/server
Ponder é documentação visual e não gameplay authority. Rotas, items segurados, Skillet state, recipe completion e transferência são server-authoritative. O cliente pode animar o Arm, mas não decidir que um dish foi produzido.
## 7. Jar-in-jar físico
O JAR embarca **L2Core, L2ModularBlocks e L2Serial** internamente. Esses componentes são dependências empacotadas do parent e não devem virar três linhas top-level no catálogo. Version drift desses internals pode afetar serialization/networking do addon, mas o owner operacional continua o parent `create_cuisine`.
## 8. Lifecycle e multiplayer
Testar chunk unload com Arm no meio de input/stir/output, restart, recipe reload e dois Arms competindo pela mesma Skillet. A ordem de route exits deve persistir conforme Create e não gerar segunda retirada após reload.
## 9. Riscos
1. Package é consumido sem entregar food ou duplica remainder.
2. Spatula continua stir loop após skillet vazia.
3. Plate extrai o mesmo dish duas vezes.
4. Arm entrega output e Skillet mantém cópia.
5. Destination full causa loss/dupe.
6. Recipe reload deixa cooking state stale.
7. Dois Arms competem e ambos recebem o mesmo output.
8. Jar-in-jar/API drift quebra serialization/interaction.
## 10. Boundary para quests/perks
Abrir Ponder, inserir ingrediente ou iniciar stir não significa dish concluído. Crédito deve ocorrer após Cuisine Delight confirmar output final e a transferência server-side ser deduplicada.
## 11. Matriz de testes
- [ ] Dedicated server inicia com Arm-made Cuisine 1.0.0 + Create 6.0.10 + Cuisine Delight 1.2.10.
- [ ] Arm insere ingrediente em Skillet aquecida exatamente uma vez.
- [ ] Package entrega conteúdo sem dupe/loss do container.
- [ ] Spatula mexe apenas enquanto food está presente.
- [ ] Plate retira um único dish concluído.
- [ ] Destination cheio não perde nem duplica output.
- [ ] Dois Arms disputando a mesma Skillet mantêm exactly-once.
- [ ] Unload/restart no meio do processo retoma ou aborta de modo consistente.
- [ ] Ponder funciona apenas como documentação visual.
Nenhum teste foi marcado como aprovado.
## 12. Evidências e limite
A publicação oficial 1.0.0 sustenta os fluxos Mechanical Arm↔Skillet, packages, Spatula, Plate e Ponder. A build NeoForge 1.21.1 publicada em 17/01/2026 declara teste contra **NeoForge 21.1.206** e **Create 6.0.8**; o pack físico atual está em **NeoForge 21.1.250** e **Create 6.0.10**, portanto é uma combinação mais nova do que o pin de teste upstream e continua exigindo smoke-test local, sem inferir incompatibilidade. O conteúdo jar-in-jar é confirmado pela modlist física. Internals de scheduling do Arm e recipe IDs de Cuisine Delight não foram inferidos sem source/runtime específico.
