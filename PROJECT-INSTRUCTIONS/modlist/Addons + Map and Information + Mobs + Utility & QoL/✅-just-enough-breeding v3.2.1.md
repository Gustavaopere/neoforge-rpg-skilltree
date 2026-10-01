# Just Enough Breeding

## Propriedades do registro

- **Mod:** Just Enough Breeding
- **Arquivo JAR:** justenoughbreeding-neoforge-1.21.1-3.2.1.jar
- **Versão 1.21.1:** 3.2.1
- **Categoria:** QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://modrinth.com/mod/justenoughbreeding/version/lVl2kVwh
- **Função:** Plugin client-side de recipe/viewer que apresenta informações de reprodução de entidades em JEI/REI/EMI; no pack atual a integração concreta é JEI 19.56.0.440.
- **Dependências:** Viewer compatível; no pack físico está JEI 19.56.0.440. O projeto também suporta REI/EMI, mas eles não são tratados como presentes sem JAR top-level correspondente. A build NeoForge 3.2.1 é classificada client-side.
- **Compatibilidade/Riscos:** Informativo apenas. Riscos: breeding data incompleta/stale para entidades modded, viewer API drift, recipe entry quebrada e confundir combinação exibida com regra authoritative do servidor. Não substitui Animal Husbandry/Animal Wellness.
- **Sobreposição:** Complementa JEI com dados de reprodução. Não substitui Animal Husbandry, Animal Wellness ou qualquer sistema de genética/reprodução; estes continuam determinando gameplay e podem expor dados que o viewer não compreenda integralmente.
- **Observações:** 3.2.1 continua a build NeoForge correta para 1.21.1. Há linha 3.3.0 para Minecraft 1.21.11, que não é update aplicável ao pack atual.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge/Modrinth oficiais Just Enough Breeding 3.2.1 NeoForge 1.21.1 de 30/07/2026 + changelog exato já auditado + JEI físico 19.56.0.440.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece JEBr 3.2.1. CurseForge publicou **3.3.0** para NeoForge 1.21.1 em 30/09/2026; o delta funcional da linha 3.3.0 foi revisado e registrado abaixo.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #354: JAR `justenoughbreeding-neoforge-1.21.1-3.2.1.jar`, mod id `justenoughbreeding`, runtime `3.2.1`, SHA-1 `9b2bfac5e7aa0952dfb3da18936c0ece4beeae21`.

<callout icon="🐾" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `justenoughbreeding-neoforge-1.21.1-3.2.1.jar`, mod id `justenoughbreeding`, versão `3.2.1`. A release oficial NeoForge 1.21.1 é client-side e corrige duas `creaturecraft recipes` quebradas.
</callout>
## 1. Papel e authority
Just Enough Breeding é um plugin informativo para recipe viewers. Ele apresenta combinações/informações de breeding; **não executa reprodução** e não é authority de cooldown, idade, genética, sexo, tame state ou filho gerado.
## 2. Viewer físico
O pack usa **JEI 19.56.0.440**. O projeto também suporta REI/EMI, mas suporte upstream não equivale a presença física. Nesta matriz, JEI é o backend concreto a validar.
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
- [ ] Cliente inicia com JEBr 3.2.1 + JEI 19.56.0.440.
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

## 12. Atualização upstream 3.3.0 — não instalada
A autoridade física continua em **Just Enough Breeding 3.2.1**. Para NeoForge 1.21.1, a próxima release pública é **3.3.0**, publicada em 30/09/2026; não há release 3.2.x intermediária posterior a 3.2.1 nessa linha.

A linha 3.3.0 amplia substancialmente o viewer:
- **Cat** e **Wolf** passam a aparecer como domesticados com owner na breeding category;
- adiciona **Zombie Horse** à temper category;
- transformation recipes passam a aceitar `input_entity` vazio;
- adiciona tingimento de collar de **Cat** e **Wolf** à transformation category;
- adiciona variantes com chest de **Donkey, Llama e Mule** à transformation category;
- adiciona **Copper Golem deoxidation**;
- adiciona **shearing recipes** e seus outputs;
- adiciona equip de **armor e saddle** à transformation category;
- adiciona criação de **Happy Ghast**;
- adiciona montagem do **Happy Ghast Harness**;
- ajusta scaling/movimento do mob com botões de offset;
- permite habilitar/desabilitar os **offset buttons** em `justenougbreeding.json`; por padrão permanecem habilitados.

Impacto para o pack: 3.3.0 expande JEBr além de breeding estrito para transformations/temper/equipment de entidades. Isso continua sendo **observabilidade/UI**, não authority de tame, shearing, oxidation, saddle/armor ou transformação real. O servidor/provider de cada entidade continua decidindo o resultado.

Gate de promoção: JEI 19.56.0.440; Cat/Wolf owner display; transformation com input vazio; collar dye; chested donkey/llama/mule; shearing outputs; armor/saddle; offset buttons on/off; reload de config/JEI; ausência de recipe duplicada; comparação com gameplay real de uma amostra de providers.

Fonte upstream: CurseForge file ID 9019426, `justenoughbreeding-neoforge-1.21.1-3.3.0.jar`. O changelog da mesma release 3.3.0 explicita os deltas acima.