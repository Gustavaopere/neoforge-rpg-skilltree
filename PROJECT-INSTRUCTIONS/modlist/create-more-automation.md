# Create: More Automation

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818b8c63fea2b613cb23
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: More Automation
- **Arquivo JAR:** `create_more_automation-0.5.2-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 0.5.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Tecnologia
- **Função:** Recipe-focused addon de Create que adiciona métodos e rotas extras para automatizar itens/blocos vanilla e Create por meio dos processing types existentes.
- **Dependências:** Create; JEI é opcional e recomendado pelo projeto para visualizar as mudanças/adições de receitas. Build 0.5.2 é Client & Server, NeoForge 1.21.1, linha reescrita para Create 6.0.
- **Sobreposição:** Sobrepõe objetivo de outros recipe packs de automação, mas não é redundância automática. A comparação deve ser recipe-by-recipe, considerando custo, throughput e progression gate.
- **Compatibilidade/Riscos:** Altera economia/progressão por recipes alternativos. A linha NeoForge 1.21.1 possui recipes significativamente diferentes da antiga 1.20.1; 0.5.2 modifica especificamente recipes de Ice e Moss. Risco principal é recipe overlap/double-route com outros recipe packs, não conflito binário.
- **Observações:** mod id `create_more_automation`; runtime 0.5.2. Projeto: muitos recipes para automatizar Create/vanilla; JEI recomendado. 0.5.2: Ice recipe modificado novamente e Moss recipe alterado para usar stone em vez de cobblestone.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_more_automation` 0.5.2 + Modrinth/CurseForge oficiais da release 0.5.2 para NeoForge 1.21.1/Create 6.0.
- **Fonte:** https://modrinth.com/mod/create-more-automation/version/en1TN4J7
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — recipe/data authority, Create processing boundary, JEI role, progression overlap, reload lifecycle e deltas 0.5.2 de Ice/Moss catalogados.
- **Histórico da decisão:** Houve recomendação histórica de remoção sob uma arquitetura antiga centrada em TFC. Esse fundamento não é operacional no pack atual e não constitui decisão formal vigente. Em 08/09/2026, o mod foi reconfirmado como `Instalado`; permanece `Sem decisão` e a antiga data associada foi removida.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🏭 Versão física confirmada: `create_more_automation-0.5.2-neoforge-1.21.1.jar`, mod id `create_more_automation`, runtime `0.5.2`. Esta é a linha **NeoForge 1.21.1 / Create 6.0**, com recipes diferentes da branch antiga 1.20.1.

## 1. Papel e authority
Create: More Automation é principalmente um **recipe/data addon**. Ele amplia o que pode ser produzido automaticamente usando processing types do Create. Create continua authority do processamento mecânico; o addon decide quais recipes adicionais existem.

## 2. Escopo funcional
O projeto declara adicionar muitos novos recipes para automatizar itens e blocos vanilla/Create. Não tratar isso como um novo machine framework: o valor do addon está na cobertura de recipes e rotas alternativas.

## 3. Linha 1.21.1 versus 1.20.1
O upstream mantém duas linhas distintas e avisa que a versão NeoForge 1.21.1, pós-Create 6.0, possui **recipes significativamente alterados** e ainda está em desenvolvimento. Recipes de guias 1.20.1 não podem ser copiados para esta ficha como se fossem equivalentes.

## 4. Ice recipe — 0.5.2
A release 0.5.2 registra nova alteração no **Ice recipe**. O recipe manager/data pack da build instalada é a authority; documentação ou scripts baseados em uma revisão anterior precisam ser revalidados.

## 5. Moss recipe — 0.5.2
A 0.5.2 altera o recipe de **Moss** para usar **stone em vez de cobblestone**. Esse detalhe afeta custo/progressão e é regression gate direto da versão física.

## 6. Recipe overlap
Outros addons podem registrar caminhos para o mesmo output. Coexistência é permitida, mas pode reduzir custos ou contornar progression gates quando duas rotas ficam disponíveis.
A auditoria final de redundância deve comparar input, processing type, duration e output reais, não apenas nomes de itens.

## 7. Create processing
Mixing, crushing, pressing, compacting, filling ou outras primitives pertencem ao Create. More Automation registra recipes sobre essas primitives; não deve executar um segundo settlement além do processamento Create normal.

## 8. JEI
JEI é recomendado pelo projeto para visualizar recipes e alterações. **JEI não é recipe authority**: se a visualização divergir do Recipe Manager, o runtime/server prevalece.

## 9. Datapack e overrides
Como o valor principal está em recipes, KubeJS/datapacks do pack podem potencialmente substituir/remover rotas. Toda customização deve usar IDs/recipe types reais e verificar se não deixa duas receitas equivalentes ativas por acidente.

## 10. Client/server
Recipe resolution e output são common/server-authoritative. JEI e interfaces são client-facing.
Multiplayer deve produzir exatamente o mesmo output para a mesma receita aceita pelo servidor, independentemente do viewer do cliente.

## 11. Lifecycle
Validar startup, datapack/recipe reload, server restart e updates do Create. Recipe caches/viewers devem refletir o conjunto atual após reload sem manter entradas stale.

## 12. Riscos
1. Recipe alternativo quebrar progression gate.
2. Mesmo output ficar excessivamente barato por duas rotas.
3. Recipe 1.20.1 ser aplicado à branch 1.21.1 por documentação antiga.
4. Ice/Moss manterem recipe anterior após update.
5. JEI exibir estado stale após reload.
6. Script externo duplicar recipe com ID diferente.

## 13. Matriz de testes
1. Dedicated server boot.
2. Recipe reload sem erro.
3. Validar Ice conforme 0.5.2.
4. Validar Moss usando stone, não cobblestone.
5. Amostra de recipes vanilla/Create adicionados pelo addon.
6. Comparar outputs com outros recipe packs instalados.
7. JEI versus Recipe Manager após reload.
8. Restart preservando o mesmo conjunto de recipes.

## 14. Evidência
- modlist física 08/09/2026: 0.5.2;
- Modrinth oficial: NeoForge 1.21.1, Client & Server, Create required, JEI optional;
- documentação oficial: addon de novos métodos/recipes de automação;
- changelog 0.5.2: Ice recipe alterado novamente; Moss passa a usar stone.

> 🔒 Boundary canônico: **More Automation decide recipes extras; Create decide a execução das máquinas**. Redundância só existe quando recipes concretos se sobrepõem em custo e função.