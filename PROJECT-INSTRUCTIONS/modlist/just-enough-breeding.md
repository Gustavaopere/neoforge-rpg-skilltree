# Just Enough Breeding

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81349c14c440b64101b0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — addon e JEI confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Just Enough Breeding
- **Arquivo JAR:** `justenoughbreeding-neoforge-1.21.1-3.2.1.jar`
- **Versão 1.21.1:** 3.2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Plugin client-side de recipe/viewer que apresenta informações de reprodução de entidades em JEI/REI/EMI; no pack atual a integração concreta é JEI 19.53.0.426.
- **Dependências:** Viewer compatível; no pack físico está JEI 19.53.0.426. O projeto também suporta REI/EMI, mas eles não são tratados como presentes sem JAR top-level correspondente. A build NeoForge 3.2.1 é classificada client-side.
- **Sobreposição:** Complementa JEI com dados de reprodução. Não substitui Animal Husbandry, Animal Wellness ou qualquer sistema de genética/reprodução; estes continuam determinando gameplay e podem expor dados que o viewer não compreenda integralmente.
- **Compatibilidade/Riscos:** Informativo apenas. Riscos: breeding data incompleta/stale para entidades modded, viewer API drift, recipe entry quebrada e confundir combinação exibida com regra authoritative do servidor. Não substitui Animal Husbandry/Animal Wellness.
- **Observações:** 3.2.1 corrige duas `creaturecraft recipes` quebradas. O addon exibe informação; breeding cooldown, idade, sexo/genética e resultado real continuam pertencendo ao provider da entidade/sistema de criação.
- **Procedência:** modlist.txt física atual + Modrinth/CurseForge oficiais Just Enough Breeding 3.2.1 NeoForge 1.21.1 de 30/07/2026 + changelog exato 3.2.1 + JEI físico 19.53.0.426.
- **Fonte:** https://modrinth.com/mod/justenoughbreeding/version/lVl2kVwh
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Just Enough Breeding 3.2.1 release-pinned; breeding viewer/data authority, JEI integration, modded-entity boundaries, 3.2.1 creaturecraft fix, client lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `justenoughbreeding-neoforge-1.21.1-3.2.1.jar`, mod id `justenoughbreeding`, versão `3.2.1`. A release oficial NeoForge 1.21.1 é client-side e corrige duas `creaturecraft recipes` quebradas.

## 1. Papel e authority
Just Enough Breeding é um plugin informativo para recipe viewers. Ele apresenta combinações/informações de breeding; **não executa reprodução** e não é authority de cooldown, idade, genética, sexo, tame state ou filho gerado.

## 2. Viewer físico
O pack usa **JEI 19.53.0.426**. O projeto também suporta REI/EMI, mas suporte upstream não equivale a presença física. Nesta matriz, JEI é o backend concreto a validar.

## 3. Entidades vanilla e modded
A utilidade do plugin depende dos dados que consegue obter/registrar para cada entidade. Entidades de mods podem possuir requisitos próprios que não cabem em um par simples de ingredientes. Uma entrada ausente no viewer não significa que a entidade seja infértil.

## 4. Breeding data vs gameplay
A recipe exibida é documentação/UI. O servidor e o mod da entidade continuam decidindo se dois mobs podem reproduzir, quais itens são válidos e qual offspring nasce. Quests não devem creditar breeding pela abertura da recipe.

## 5. Relação com Animal Husbandry/Animal Wellness
Esses sistemas alteram/expandem gameplay animal no pack. Just Enough Breeding não os substitui. Se um deles modificar regras após o viewer registrar dados, pode haver divergência visual que precisa ser resolvida a favor do runtime authoritative.

## 6. Release 3.2.1
O changelog exato informa **fix de duas broken creaturecraft recipes**. Isso é regression gate de apresentação/data do addon; não se infere mudança na lógica de reprodução das criaturas correspondentes.

## 7. Client / server
A build 3.2.1 é classificada client-side. Ela pode ler dados sincronizados pelo jogo/mods, mas não deve alterar world state. Dedicated server não deve depender de JEBr para resolver reprodução.

## 8. Lifecycle
Validar client boot, JEI plugin registration, world join, recipe reload, language/resource reload e update da modlist. Entries devem ser reconstruídas sem duplicar receitas ou manter entidades removidas.

## 9. Riscos técnicos
- breeding entry faltante para mob modded;
- combinação exibida stale após update de provider;
- recipe duplicada;
- JEI API drift;
- creaturecraft entry quebrada regressar;
- tratar UI como regra server-side;
- resource reload manter cache antigo;
- conflito puramente visual com outro breeding viewer.

## 10. Matriz de testes obrigatória
- [ ] Cliente inicia com JEBr 3.2.1 + JEI 19.53.0.426.
- [ ] Category/entries registram sem JEI API error.
- [ ] Amostra vanilla mostra breeding info coerente.
- [ ] Amostra de animais modded abre sem crash.
- [ ] As duas creaturecraft recipes corrigidas não aparecem quebradas.
- [ ] Recipe reload não duplica entries.
- [ ] Remover/alterar entidade não deixa entry órfã.
- [ ] Gameplay de breeding continua server-authoritative.
- [ ] Animal Husbandry/Wellness não são inferidos como substituídos.

## 11. Evidências e limites
- **Modlist física:** JAR/mod id/version e JEI atual.
- **Modrinth/CurseForge oficiais:** Release 3.2.1, ambiente client-side e função JEI/REI/EMI.
- **Changelog 3.2.1:** fix de duas creaturecraft recipes.
- **Limite:** catálogo exato de todas as entidades/receitas carregadas não foi extraído; compat de cada mob modded permanece runtime-dependent.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
