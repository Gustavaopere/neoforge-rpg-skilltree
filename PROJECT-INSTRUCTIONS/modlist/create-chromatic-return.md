# Create: Chromatic Return

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d1951df0e7c08ef3e2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR, mod id e runtime confirmados fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Chromatic Return
- **Arquivo JAR:** `createchromaticreturn-1.0.4-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, RPG, Automação
- **Função:** Reintroduz/expande materiais cromáticos, ferramentas, armas, charms e itens poderosos ligados ao Create, incluindo rotas de enriquecimento de quartzo/minérios.
- **Dependências:** Página oficial 1.21.1 declara Create + Create Quality of Life + Curios; pack físico contém Create 6.0.10, CreateQOL 1.6.3-fix1 e Curios 9.5.1. Integrações publicadas com CC&A e Create Stuff 'N Additions têm providers instalados.
- **Sobreposição:** Overlap principal é econômico/progressivo com Metallurgy/Metalwork e outras rotas de ore processing/Netherite/Brass. CC&A/CSA são integrações publicadas; Mekanism não está presente no pack.
- **Compatibilidade/Riscos:** Riscos: Creative recipes/Multiplite trivializam progressão; Quartz Enrichment 4x empilha com outras rotas; Super Silk Touch/spawner; Creative Flight stale; recipes CC&A/CSA drift; filename 1.0.4 ↔ runtime 1.0.0; MCreator internals sem source matching.
- **Observações:** JAR físico `createchromaticreturn-1.0.4-neoforge-1.21.1.jar`, mod id `createchromaticreturn`, runtime metadata `1.0.0`; MCreator. Publicação/filename 1.0.4 e runtime 1.0.0 permanecem separados.
- **Procedência:** modlist.txt física atual de 09/09/2026 — 594 JARs top-level + metadata runtime + CurseForge oficial Create: Chromatic Return 1.0.4 e documentação funcional atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-chromaticreturn
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê com alloys, Multiplite/Creative items, Glow tools, Infused Books, charms, Quartz Enrichment 2x/4x, compats e version mismatch catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🌈 **Identidade física confirmada com divergência preservada:** `createchromaticreturn-1.0.4-neoforge-1.21.1.jar`, mod id `createchromaticreturn`, runtime metadata `1.0.0`. A publicação oficial/filename é **1.0.4**; o JAR é identificado como MCreator. Não normalizar a metadata física para 1.0.4.

## 1. Papel e authority
Create: Chromatic Return é uma expansão end-game do Create centrada em **Chromatic Compound, alloys avançados, tools/weapons, charms e rotas de automação de alto poder**. O addon owns seus materiais, itens, efeitos e recipes; Create continua owner de mixers/basins/heat/processos que essas recipes utilizam.

## 2. Dependências publicadas e pack
A página oficial atual para 1.21.1 informa Create, Create Quality of Life e Curios como dependências do projeto. O pack contém Create 6.0.10, Create Quality of Life 1.6.3-fix1 e Curios 9.5.1+1.21.1.
Como não há source matching público do artefato MCreator 1.0.4, não são inventados ranges mínimos ou hooks internos além do que a publicação confirma.

## 3. Chromatic Compound e alloys
A documentação atual apresenta uma cadeia de alloys derivados do domínio Chromatic, incluindo **Multiplite, Anti-Plite, Industrium, Durasteel, Fortunite e Silkstrum**, além de outros materiais da linha do projeto.
Cada alloy possui intenção de progressão própria; equivalência por tag/material com mods externos não deve ser presumida apenas por cor/nome.

## 4. Multiplite
Multiplite é apresentado como material end-game ligado a recursos de energia/creative power. O projeto o usa para recipes extremamente fortes, inclusive itens do tipo Creative.
Isso torna qualquer rota que reduza seu custo ou o torne renovável um risco de quebra de progressão de todo o pack.

## 5. Creative items craftáveis
A documentação confirma acesso craftável a itens de classe Creative, incluindo exemplos como **Creative Motors, Blaze Cakes, Generators e Fueling Tanks**, com comportamento de recursos infinitos conforme o item/provider correspondente.
Essas recipes são deliberadamente overpowered. Não devem ser tratadas como QoL neutro: são uma policy de progression do pack.

## 6. Glow Saber e Glow Claws
O projeto publica **Multiplite Glow Saber** e **Glow Claws** como ferramentas/armas end-game extremamente fortes, incluindo one-hit de mobs e, para as Claws, quebra extrema de blocos na descrição pública.
Dano, mining rules e proteção de blocos precisam ser verificados no servidor; claims promocionais não substituem testes de interaction com claims/proteções/world safety.

## 7. Shadow Steel tools e cast parts
A linha documenta Shadow Steel como base de tool parts/casting para ferramentas com propriedades especiais. O pipeline de parts e montagem pertence ao addon.
Scripts que substituam recipes precisam preservar components e não criar combinações impossíveis ou loss de material.

## 8. Infused Books
O projeto possui Infused Books com efeitos/enchantments customizados, incluindo **Durable** e **Super Silk Touch** segundo a documentação atual. Super Silk Touch é descrito como capaz de trabalhar com spawners em determinados tools.
Esse comportamento é altamente sensível a segurança/progressão; spawner pickup precisa ser validado contra outros mods de spawner e regras do servidor.

## 9. Charms
Charms de alloys concedem efeitos permanentes enquanto mantidos no slot/condição publicada, com exemplos de Speed, Strength e **Creative Flight**.
Effect grant/revoke precisa ser server-authoritative e removido imediatamente quando a condição deixa de ser válida. Não permitir efeito fantasma após death/relog/item move.

## 10. Quartz Enrichment — 2x/4x
A documentação oficial confirma **ore doubling** com Quartz + crushed ore ou Silk-Touched gem ore em Heated Blaze Burner Basin e novo doubling com **Fortunite + Super-Heated Basin**, chegando a até **4x yield**.
Essa é uma das maiores superfícies econômicas do addon e deve ser comparada com Create Metallurgy/Metalwork e outras rotas de processamento do pack.

## 11. Recipes de simplificação
O projeto publica receitas adicionais para automatizar Netherrack, redstone/dusts via cinder flour e outras cadeias, além de rotas para Netherite e Brass em sua linha funcional.
Recipe Manager real é authority para inputs/outputs da build 1.0.4; não transportar quantities da linha 1.20.1 sem verificação.

## 12. Compat Create Crafts & Additions
A página oficial registra suporte Create Addition com recipe de **Creative Generator**. O pack contém Create Crafts & Additions 1.7.0, portanto essa integração é potencialmente ativa e precisa ser validada pelo recipe realmente carregado.
O provider CC&A continua owner do item/máquina alvo.

## 13. Compat Create Stuff 'N Additions
O projeto registra suporte Create Stuff Addition com recipe de **Creative Filling Tank**. O pack contém CSA 2.1.4b.
A compatibilidade precisa ser testada com a versão física atual; não assumir que o recipe legado encontra IDs inalterados sem consultar o runtime.

## 14. Mekanism — não ativo no pack
A documentação também cita suporte a Mekanism, mas **Mekanism não está presente** na modlist física atual. Essa superfície permanece upstream e não deve aparecer como integração runtime ativa.

## 15. Creative recipe policy
A própria documentação pública oferece um exemplo de remoção de `createchromaticreturn:multiplite_recipe` via scripting enquanto não há opção oficial equivalente. Isso demonstra que acesso aos Creative recipes é uma escolha explícita de balanceamento.
No pack, qualquer bloqueio/alteração deve ser feito de forma consciente e testada, não silenciosamente nesta catalogação.

## 16. Divergência de versionamento
Há uma divergência objetiva: filename/publicação **1.0.4**, metadata runtime física **1.0.0**. Ferramentas de dependency/version detection podem reportar 1.0.0 mesmo quando o arquivo baixado é 1.0.4.
Catalogação, troubleshooting e scripts devem preservar os dois identificadores.

## 17. MCreator e limite de internals
A modlist identifica o JAR como MCreator. Isso é apenas detalhe de implementação, não motivo de remoção. Sem source matching/public registry dump, classes, procedures, event hooks e IDs não publicados ficam fail-closed.

## 18. Client/server e multiplayer
Recipes, effect grants, damage, mining, creative-resource behavior e inventory changes são server-authoritative. Models, particles e tool visuals são client-facing.
Dois clientes não podem obter duplicação de output ou efeitos permanentes por troca rápida de slots/items.

## 19. Data/reload
Recipes podem sofrer modificação por datapacks/KubeJS/CraftTweaker. `/reload` deve substituir a tabela de recipes sem duplicar outputs ou deixar rota antiga ativa no viewer/cache.
Mudanças em creative recipes precisam ser verificadas em JEI e, principalmente, no Recipe Manager do servidor.

## 20. Riscos
1. Multiplite/Creative recipes trivializam progressão/energia.
2. Quartz Enrichment 4x combina com outro ore multiplication e gera yield excessivo.
3. Glow tools ignoram proteção/world safety.
4. Super Silk Touch permite spawner progression não desejada.
5. Charm de Creative Flight persiste após remover item/death.
6. Recipe CC&A/CSA aponta para ID obsoleto após update.
7. Divergência 1.0.4↔runtime 1.0.0 quebra version checks.
8. Script de remoção de creative recipe conflita com outro datapack.
9. Material/tag de outro mod é aceito semanticamente de forma incorreta.
10. Recipe reload deixa rota duplicada/stale.

## 21. Matriz de testes
- [ ] Dedicated server inicia com artefato 1.0.4/runtime 1.0.0 + Create 6.0.10 + CreateQOL + Curios.
- [ ] Alloys principais são obtidos apenas pelas rotas realmente carregadas.
- [ ] Multiplite/Creative items respeitam a policy de progressão desejada.
- [ ] Quartz Enrichment entrega 2x/4x sem combinar em loop positivo com outras rotas.
- [ ] Glow Saber/Claws respeitam regras de servidor/proteção relevantes.
- [ ] Super Silk Touch é testado especificamente com spawners.
- [ ] Charms removem efeitos imediatamente ao perder a condição de uso.
- [ ] Creative Flight não persiste após relog/death/item move.
- [ ] Compat CC&A 1.7.0 resolve os items/recipes reais.
- [ ] Compat CSA 2.1.4b resolve os items/recipes reais.
- [ ] `/reload` não duplica recipes nem outputs.
- [ ] Ferramentas de diagnóstico preservam filename 1.0.4 e runtime 1.0.0 separadamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
A modlist física confirma JAR 1.0.4, mod id e runtime 1.0.0. CurseForge oficial confirma 1.0.4 NeoForge 1.21.1 Client & Server e documenta alloys, tools, charms, Creative recipes, ore doubling/quadrupling e compats. Não foi localizado source matching da build; internals e quantities não publicadas permanecem fail-closed.

> 🔒 **Boundary canônico:** Chromatic Return owns seus materiais/efeitos/recipes; Create e addons-alvo own suas máquinas/items. O maior risco aqui é econômico/progressivo, especialmente Creative items e multiplicação 4x — não uma simples duplicidade de conteúdo.