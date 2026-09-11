# Integrated Simply Swords

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db816daec4f1db703cce91
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated Simply Swords
- **Arquivo JAR:** `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.4.0+1.21.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, RPG
- **Função:** Addon de Simply Swords que adiciona variantes dos tipos de armas para materiais de outros mods, preenchendo lacunas de toolsets e preservando o framework de combate/equipamento do Simply Swords.
- **Dependências:** Obrigatórias oficiais e presentes: Simply Swords 1.70.2-1.21.1, Architectury API 13.0.11 e Cloth Config 15.0.140. Integrações de material são condicionais ao provider correspondente.
- **Sobreposição:** Complementa Simply Swords; não substitui o mod-base. Pode sobrepor outros addons que criem armas dos mesmos materiais; comparar item/recipe IDs, tags, stats e progressão antes de manter variantes duplicadas.
- **Compatibilidade/Riscos:** Addon de material/weapon variants sensível a registry/tag drift. Riscos: material duplicado por múltiplos addons, recipe progression paralela, stats fora da curva, textura/resource-pack divergence e assumir suporte ao Ice and Fire CE fork apenas porque upstream cita Ice and Fire original.
- **Observações:** Release oficial 1.4.0 para NeoForge 1.21.1 publicada em 29/05/2026. Upstream lista vários providers suportados; no pack Born in Chaos está confirmado. Ice and Fire CE 2.1.2 é fork e sua cobertura não é presumida sem teste de registries/tags.
- **Procedência:** modlist.txt física atual + CurseForge oficial Integrated Simply Swords 1.4.0, Release NeoForge 1.21.1 de 29/05/2026 + descrição/relações oficiais do projeto. Source exato da build não foi pinado nesta auditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-simply-swords
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Integrated Simply Swords 1.4.0 release-pinned; material-provider admission, weapon variants, Simply Swords authority, supported-mod boundaries, client/server lifecycle, balance/registry risks e matriz de testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar`, mod id `integrated_simply_swords`, versão `1.4.0+1.21.1-neoforge`. A release oficial NeoForge 1.21.1 foi publicada em 29/05/2026. **Decisão `Manter` preservada.**

## 1. Papel e authority
Integrated Simply Swords preenche lacunas de toolsets ao criar variantes de tipos de arma do **Simply Swords** usando materiais fornecidos por outros mods. Simply Swords continua authority do framework/tipos-base de arma; cada mod integrado continua owner de seu material; esta bridge é owner apenas das variantes que registra.

## 2. Dependências obrigatórias
Upstream exige Simply Swords, Architectury API e Cloth Config API. No pack estão Simply Swords 1.70.2-1.21.1, Architectury 13.0.11 e Cloth Config 15.0.140. Falha de qualquer requisito pode impedir registry/data load.

## 3. Critério de integração de materiais
O projeto prioriza mods com conjuntos de materiais suficientemente completos, versão Minecraft compatível, texturas adequadas e adoção relevante. Isso é política editorial do addon, não contrato de que todo material de todo mod compatível terá uma variante para cada weapon type.

## 4. Variantes de armas
As novas armas combinam materiais de providers externos com os tipos de arma do Simply Swords. Stats, recipes e tags devem ser lidos da build/data efetivamente carregada. Não replicar números por analogia com espada/picareta do material, nem assumir que duas variantes visualmente semelhantes têm o mesmo contrato.

## 5. Supported mods e presença física
A documentação upstream cita integrações como Born in Chaos, Ice and Fire e outros providers. **Born in Chaos 1.7.6 está fisicamente presente**. O pack usa **Ice and Fire Community Edition 2.1.2**, não necessariamente a implementação original esperada pelo addon; compatibilidade com esse fork deve ser validada por tags/registry/recipes, e não inferida pelo nome.

## 6. Better Combat
Better Combat aparece entre integrações upstream, mas não foi encontrado como top-level na modlist física atual desta auditoria. Logo nenhuma compatibilidade Better Combat é considerada ativa no pack por esta ficha.

## 7. Recipes, tags e material ownership
Uma recipe da bridge deve consumir material pertencente ao provider e gerar exatamente uma variante Integrated/Simply Swords. KubeJS ou outro datapack não deve criar rota duplicada sem decisão explícita. Alteração de tag/material ID no provider pode quebrar recipe mesmo que o addon continue carregando.

## 8. Unique Weapons e roadmap
O projeto menciona intenção de adicionar armas únicas temáticas para alguns mods/conceitos. Isso é escopo de projeto/roadmap; não é usado para afirmar que determinada unique weapon existe na 1.4.0 sem evidência da build.

## 9. Texturas e resource-pack provenance
O autor informa que algumas texturas integradas podem se basear em recursos de packs externos e não corresponder exatamente à estética vanilla do provider. Esse detalhe é visual, mas importa ao pack por consistência estética. Mudanças de resource pack podem alterar apresentação sem mudar gameplay.

## 10. Client / server
A distribuição é Client & Server. Registro de itens, recipes, atributos e resultados de crafting são authoritative no servidor. Modelos/texturas/tooltips são client-side. O cliente não deve derivar stats pelo material visualmente exibido.

## 11. Lifecycle
Validar registry sync, data/recipe load, tag resolution, crafting, equip/unequip, combat use, death/drop, reconnect e server restart. Mudança de provider de material deve ser testada antes de abrir mundo persistente.

## 12. Balance e sobreposição
O pack possui vários sistemas de armas e progressão. O risco não é apenas duplicate registry: duas recipes diferentes podem produzir armas equivalentes com custos muito diferentes. Comparar DPS, attack speed, traits, repair material e etapa tecnológica/mágica antes de manter sobreposição funcional.

## 13. Riscos técnicos
- tag/material ID ausente após update de provider;
- duas bridges criarem variantes do mesmo material/tipo;
- recipe duplicada ou rota excessivamente barata;
- stats/traits não acompanharem o Simply Swords atual;
- textura/resource pack divergir do provider;
- assumir compatibilidade do Ice and Fire CE sem teste;
- optional integration ser tratada como hard dependency;
- client/server registry mismatch.

## 14. Matriz de testes obrigatória
- [ ] Client + dedicated server iniciam com 1.4.0 e três hard dependencies.
- [ ] Registry/data load não apresenta missing tags/items.
- [ ] Variantes dos providers realmente presentes aparecem apenas quando válidas.
- [ ] Born in Chaos integration resolve recipes/materials esperados.
- [ ] Ice and Fire CE é testado separadamente; ausência de cobertura não causa hard-fail.
- [ ] Crafting não possui duplicação/dupe de outputs.
- [ ] Equip/combat respeitam stats e traits do runtime.
- [ ] Resource reload mantém models/textures válidos.
- [ ] Update de Simply Swords/provider é bloqueado até smoke test.

## 15. Evidências e limites
- **Modlist física:** JAR/mod id/version e hard dependencies atuais.
- **CurseForge oficial:** release 1.4.0, escopo do addon, requirements e lista de integrações suportadas.
- **Limite:** source code/registry table exatos da 1.4.0 não foram pinados; não se inferem variantes individuais além do escopo publicado.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
