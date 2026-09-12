# FamiliarsLib — runtime 1.21.1-1.7

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db816aba8ff6b4c353e137  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** FamiliarsLib
- **Arquivo JAR:** `familiarslib-1.21.1-1.7.1.jar`
- **Versão 1.21.1:** `1.21.1-1.7`
- **Categoria:** Biblioteca; Magia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/familiarslib
- **Função:** Core/API do ecossistema Alshanex's Familiars: abstrações para familiars, beds, storage, player familiar state e pets melee/flying/spellcasting, além de compat/utilidades reutilizáveis.
- **Dependências:** Consumer direto confirmado: Alshanex's Familiars 1.21.1_v4.0.3. Source baseline: Iron's Spells 3.15.5, GeckoLib 4.7.5.1, Curios 9.2.2 e Player Animator 2.0.1; o pack usa Iron's 3.16.3 e versões atuais das demais bases, portanto há regression gate de API.
- **Compatibilidade/Riscos:** Library transversal de familiar state/storage/casting. Riscos: API drift, stale PlayerFamiliarData, storage dupe, owner loss em dimension change, double spell settlement e client classloading. Filename/publicação 1.7.1 e metadata/source `1.21.1-1.7` são divergência real preservada.
- **Sobreposição:** Não é duplicata do Alshanex's Familiars: é sua biblioteca/core. Iron's Spells, Curios, GeckoLib e Player Animator permanecem providers de seus próprios contratos.
- **Observações:** Não normalizar `1.7.1` para `1.21.1-1.7` nem vice-versa: filename/publicação e metadata/source usam strings diferentes. Source confirma AbstractFamiliarBed/Storage, PlayerFamiliarData, spellcasting pet bases e compat Curios.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `familiarslib-1.21.1-1.7.1.jar`, mod id `familiarslib`, metadata version `1.21.1-1.7` e SHA-1 7fa3f3116e35c12456425ae195924ced33fcc2eb. Source oficial também declara `mod_version=1.21.1-1.7`; CurseForge publica filename/release 1.7.1.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — FamiliarsLib: versão física/source reconciliada, abstrações de familiar/bed/storage, player state, spellcasting pets, version drift, lifecycle, multiplayer, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `familiarslib-1.21.1-1.7.1.jar` · mod id `familiarslib` · metadata runtime `1.21.1-1.7` · NeoForge 1.21.1. O filename/publicação usa `1.7.1`, enquanto o próprio source declara `mod_version=1.21.1-1.7`; as duas strings são preservadas, sem normalização artificial.

## 1. Papel no modpack
FamiliarsLib é a biblioteca/core do ecossistema Alshanex's Familiars. Ela fornece abstrações e infraestrutura reutilizável para mods consumidores criarem familiares, camas de familiar, armazenamento de familiar, pets que conjuram spells e integrações relacionadas. Não deve ser tratada como um content mod autônomo equivalente ao Alshanex's Familiars.

## 2. Authority / ownership
- **FamiliarsLib:** contratos base, classes abstratas, estado comum e utilidades consumidas por addons de familiares.
- **Alshanex's Familiars:** familiares concretos, itens, progressão e conteúdo que registra.
- **Iron's Spells:** framework de spell/mana/casting quando um familiar usa spell desse ecossistema.
- **Curios:** slot/equip state quando uma integração usa Curios.

A biblioteca não deve duplicar spell settlement, ownership do pet ou inventário do consumer.

## 3. Superfícies source-confirmed
O source público 1.21.1 contém abstrações como `AbstractFamiliarBedBlock`, `AbstractFamiliarStorageBlock`, seus BlockEntities correspondentes, `PlayerFamiliarData` e classes de pets spellcasting como `AbstractFlyingSpellCastingPet`, `AbstractFlyingMeleeSpellCastingPet` e `AbstractMeleeSpellCastingPet`.

Também existe `SimpleAdvancementTrigger` e uma camada `compat/FamiliarsCurios`. Esses nomes confirmam o tipo de contrato exposto; não significam que FamiliarsLib registre sozinho todos os familiares/conteúdo final.

## 4. Familiar beds e storage
As classes abstratas de bed/storage concentram persistência e interação que consumidores especializam. O provider concreto deve continuar responsável pelos blocos/BlockEntities que registra, enquanto a lib fornece comportamento reutilizável.

Regression gates: save/reload, block break, chunk unload, acesso concorrente ao storage e remoção/invalidação de familiar associado.

## 5. Player familiar state
`PlayerFamiliarData` confirma que existe estado por jogador ligado ao sistema de familiares. Esse estado precisa ser tratado como server-authoritative quando impacta ownership, vínculo ou disponibilidade de familiar; cliente deve apenas refletir estado sincronizado.

## 6. Spellcasting pets
As classes abstratas de pets melee/flying/spellcasting demonstram que a lib oferece base para entidades consumidoras combinarem AI de pet com casting. Iron's Spells continua authority da spell executada; o familiar/consumer decide quando e como solicita o cast dentro do contrato permitido.

Não duplicar damage, cooldown ou mana por fora do provider.

## 7. Dependências e version drift
O `gradle.properties` do source 1.21.1 declara Minecraft 1.21.1, NeoForge 21.1.90, Iron's Spells 3.15.5, GeckoLib 4.7.5.1, Curios 9.2.2 e Player Animator 2.0.1 como baseline de desenvolvimento. O pack usa versões posteriores em algumas dessas bases, especialmente Iron's Spells 3.16.3.

Isso é **version drift a testar**, não incompatibilidade comprovada.

## 8. Configuração e dados
O source contém `Config.java`; valores efetivos da instância devem ser lidos do config real antes de documentar defaults específicos. Estado de familiar/player e storage precisa sobreviver a reloads sem duplicação ou stale references.

## 9. Client / Server
**Servidor/common:** ownership de familiar, storage real, AI relevante, cast válido e persistência do jogador/entidade.

**Cliente:** renderer/model/animation e presentation derivados do estado sincronizado.

Biblioteca não deve carregar renderer como gameplay authority no dedicated server.

## 10. Lifecycle
Validar login/relogin, criação/remoção de familiar, death/respawn do jogador, morte/despawn do pet, dimension change, chunk unload/reload, storage open/close, server restart, datapack/resource reload e update de Alshanex's Familiars/Iron's/Curios.

## 11. Multiplayer
Cada familiar precisa manter owner correto. Dois jogadores não podem reivindicar o mesmo state por race condition; storage não pode duplicar items por interação concorrente; cast de familiar precisa liquidar exatamente uma vez no servidor.

## 12. Integrações concretas no pack
**Alshanex's Familiars 1.21.1_v4.0.3** está instalado e é o consumidor direto evidente. Iron's Spells, Curios e GeckoLib também fazem parte do stack local e correspondem às superfícies de desenvolvimento declaradas pela lib.

## 13. Riscos
1. version drift Iron's/Curios/GeckoLib;
2. stale `PlayerFamiliarData` após respawn/relog;
3. storage dupe em acesso concorrente;
4. familiar perder owner em dimension change;
5. cast/damage duplicado entre pet e spell provider;
6. block entity de bed/storage não invalidar referência;
7. consumer depender de API alterada;
8. client classloading em dedicated server;
9. animação divergir do spell state real;
10. remover a lib por confundi-la com conteúdo redundante.

## 14. Matriz de testes
1. Dedicated server boot com FamiliarsLib + Alshanex's Familiars.
2. Spawn/vínculo de familiar e relog.
3. Death/respawn do owner.
4. Dimension change com familiar ativo.
5. Bed save/reload e chunk unload.
6. Storage com dois jogadores simultâneos.
7. Familiar melee e familiar spellcasting.
8. Cast observado por dois clients, sem double damage.
9. Resource reload/client model reload.
10. Smoke-test após update de Iron's Spells/Curios/GeckoLib.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica de 08/09/2026: JAR, mod id, metadata version e hash;
- source oficial `Alshanex/FamiliarsLib`: árvore 1.21.1, abstrações de bed/storage/pets, `PlayerFamiliarData`, compat Curios e gradle properties;
- CurseForge oficial: release `familiarslib-1.21.1-1.7.1.jar` e papel de utility library.

> **Boundary canônico:** FamiliarsLib fornece **infraestrutura**; o conteúdo concreto permanece authority do mod consumidor e as spells permanecem authority do framework de magia correspondente.