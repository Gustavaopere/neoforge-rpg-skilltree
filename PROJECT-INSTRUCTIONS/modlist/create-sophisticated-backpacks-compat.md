# Create: Sophisticated Backpacks Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8112ab47f26caaa78805
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Sophisticated Backpacks Compat
- **Arquivo JAR:** `create_sophback_compat-1.0.jar`
- **Versão 1.21.1:**
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Armazenamento, Automação
- **Função:** Addon recipe-focused que adiciona receitas selecionadas de máquinas Create para componentes do Sophisticated Backpacks, preservando o sistema e a progressão-base do provider.
- **Dependências:** Create 6.0.10 + Sophisticated Backpacks 3.26.2 no pack; Sophisticated Core 1.5.1 também presente como infraestrutura do provider. Runtime do compat declara versão vazia.
- **Sobreposição:** Complementa `sophisticatedbackpackscreateintegration` 0.2.0: este compat adiciona recipes Create; o outro integra backpacks a contraptions. Não são duplicatas globais.
- **Compatibilidade/Riscos:** Metadata física defeituosa: display name aponta para outro compat e runtime version é vazia. Riscos: recipe duplicada/override por datapack/KubeJS, output/custo divergente da progressão, viewer stale e confusão com Sophisticated Backpacks Create Integration. Não normalizar runtime vazio para 1.0.
- **Observações:** JAR físico `create_sophback_compat-1.0.jar`; mod id `create_sophback_compat`; runtime display name `Create Oh The Biomes We ve Gone Compat`; runtime version vazia. `1.0` é rótulo do filename/publicação oficial, não metadata runtime.
- **Procedência:** modlist.txt física atual de 09/09/2026 — 594 JARs top-level + metadata runtime + publicação oficial Create: Sophisticated Backpacks Compat 1.0 e galeria oficial de recipes.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-sophisticated-backpacks-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo de recipes/ownership, metadata defeituosa, deployment server-side, overlap com contraption integration, reload/lifecycle e regression matrix catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🎒 **Identidade física confirmada:** `create_sophback_compat-1.0.jar`, mod id `create_sophback_compat`. A metadata runtime física apresenta nome interno incorreto (`Create Oh The Biomes We ve Gone Compat`) e **versão vazia**. O rótulo `1.0` vem do filename/publicação oficial e não deve substituir artificialmente a versão runtime ausente.

## 1. Papel e authority
Create: Sophisticated Backpacks Compat é um addon **recipe-focused** que conecta Create a Sophisticated Backpacks. Ele adiciona uma seleção de receitas de máquinas Create para componentes de Sophisticated Backpacks, permitindo automatizar partes da progressão sem substituir o sistema de backpacks.
**Sophisticated Backpacks** continua owner dos backpacks, upgrades, inventários e regras funcionais; **Create** continua owner das máquinas/processos; este addon owns apenas as recipes de compatibilidade que fornece.

## 2. Escopo publicado
A descrição oficial afirma que somente receitas cuidadosamente escolhidas são substituídas/adicionadas para manter a progressão e o gameplay central do Sophisticated Backpacks intactos. Portanto não tratar o addon como conversor universal de todas as recipes do mod-base.
O Recipe Manager carregado no servidor é authority para a lista final de inputs, processos e outputs.

## 3. Exemplos de recipes confirmados publicamente
A galeria oficial mostra exemplos de integração para **Upgrade Base**, **Auto Smelting Upgrade** e **Netherite Backpack** por processos Create. Esses exemplos confirmam a natureza da compatibilidade, mas não constituem inventário exaustivo de todas as recipes do JAR.
Quantidades, etapas e recipe IDs não são inferidos sem datapack/JAR matching.

## 4. Dependências concretas no pack
O pack físico contém:
- Sophisticated Backpacks `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`, runtime 3.26.2;
- Sophisticated Core 1.5.1;
- Create 6.0.10.
A combinação é concreta, não apenas compatibilidade potencial.

## 5. Relação com Sophisticated Backpacks Create Integration
O pack também contém `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar`.
Essa integração tem outro boundary funcional: integra backpacks com **contraptions/armazenamento Create**. O presente addon adiciona **recipes de máquinas Create**. São superfícies complementares; não há base para classificá-los como duplicatas globais.

## 6. Relação com Sophisticated Storage
Sophisticated Storage e seu Create Integration também estão presentes no pack, mas este addon é nominalmente e publicamente voltado a **Sophisticated Backpacks**. Não expandir suas recipes para Sophisticated Storage por analogia sem evidência da build.

## 7. Metadata defeituosa
A metadata runtime expõe nome incompatível com a identidade funcional e não declara versão. Isso pode afetar logs, telas de mods, relatórios automáticos e ferramentas que confiem no display name/version.
Para catalogação: filename + mod id + publicação oficial identificam o artefato; a versão runtime permanece vazia porque é assim que o JAR se apresenta.

## 8. Data-driven recipes e reload
A função do mod é majoritariamente data-driven. Datapacks, KubeJS e outros recipe modifiers podem substituir, remover ou duplicar as mesmas rotas.
Após `/reload`, recipes efetivamente carregadas devem ser reconsultadas; JEI/REI/EMI é viewer, não authority de execução.

## 9. Client/server
A página oficial informa que o mod é necessário no servidor para multiplayer; em singleplayer ele precisa estar no cliente que hospeda o mundo integrado. A execução de recipe e consumo/output é server-authoritative.
Render de recipe viewer não deve ser usado para decidir se uma recipe existe no servidor.

## 10. Multiplayer
Dois jogadores ou múltiplas máquinas processando a mesma recipe não podem causar dupe/loss. O addon não deve manter player-state próprio para a função central; a integridade depende do processamento Create e do Recipe Manager do servidor.

## 11. Lifecycle
Os principais eventos de lifecycle são datapack load/reload, entrada/saída de servidor, atualização de Create/Sophisticated Backpacks e mudança de recipes por scripts. Não há evidência pública de block entities ou persistent state próprios desta build; esses internals permanecem fail-closed.

## 12. Sobreposição e economia
O addon cria rotas alternativas para componentes que também têm crafting normal em Sophisticated Backpacks. Isso é intencional, mas pode alterar custo e throughput quando combinado com outros recipe packs.
Comparar custo líquido e disponibilidade dos inputs reais antes de concluir que uma rota automatizada trivializa progression.

## 13. Riscos
1. Recipe duplicada com KubeJS/datapack/outro compat addon.
2. Output/count diverge da progressão pretendida do Sophisticated Backpacks.
3. Recipe viewer exibe rota removida/stale após reload.
4. Atualização de Sophisticated Backpacks renomeia item/tag usado pela recipe.
5. Atualização de Create altera recipe type/processo esperado.
6. Sophisticated Backpacks Create Integration é removido por engano como “duplicata”, embora cubra contraptions e não recipes.
7. Metadata defeituosa faz auditor automático identificar o mod pelo nome interno errado.
8. Ferramentas normalizam indevidamente a versão runtime vazia para `1.0`.

## 14. Matriz de testes
- [ ] Dedicated server inicia com addon + Create 6.0.10 + Sophisticated Backpacks 3.26.2.
- [ ] Recipe de Upgrade Base publicada aparece e executa conforme Recipe Manager real.
- [ ] Recipe de Auto Smelting Upgrade publicada consome/outputa exatamente uma vez.
- [ ] Recipe de Netherite Backpack publicada respeita a progressão esperada.
- [ ] Crafting normal do Sophisticated Backpacks continua disponível conforme data atual.
- [ ] `/reload` não duplica nem mantém recipes removidas.
- [ ] KubeJS/datapacks não registram rota conflitante com mesmo output/ID.
- [ ] Sophisticated Backpacks Create Integration continua funcionando em contraptions independentemente destas recipes.
- [ ] Dois clientes observam os mesmos recipes/resultados do servidor.
- [ ] Logs/ferramentas não confundem o display name defeituoso com outro mod funcional.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
A modlist física atual confirma JAR, mod id, display name defeituoso e versão runtime vazia. A publicação oficial 1.0 confirma Minecraft 1.21.1, Forge/NeoForge, ambiente Client & Server e o escopo de recipes Create selecionadas; a galeria confirma exemplos de Upgrade Base, Auto Smelting Upgrade e Netherite Backpack. Não foi localizado source público matching, portanto classes, IDs internos e lista completa de recipes permanecem fail-closed.

> 🔒 **Boundary canônico:** Sophisticated Backpacks owns os itens/progressão; Create owns as máquinas; este addon owns somente as recipes de compatibilidade publicadas/carregadas. Filename `1.0` não autoriza preencher a metadata runtime de versão que está vazia.