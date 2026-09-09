# Battle Arts API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bb86a1d5b6b89c7c54
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Battle Arts API
- **Arquivo JAR:** `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`
- **Versão 1.21.1:** 21.17.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, RPG
- **Função:** API extensível do ecossistema Battle Arts/Epic Fight para Battle Styles, Combat Arts e Proficiencies, sem substituir o pipeline base de combate.
- **Dependências:** Ecossistema Epic Fight/Battle Arts; pack confirma Epic Fight 21.17.3.1. Consumidor top-level Battle Arts separado não foi confirmado nesta checagem física específica.
- **Sobreposição:** API complementar; não substitui Epic Fight.
- **Compatibilidade/Riscos:** Double-hit/damage, desync de style/proficiency, schema datapack incompatível e callbacks de animação virando authority são os principais riscos. Linha 21.17.x renomeou `slash_modifier` para `damage_attribute_modifier`. Release 21.17.7 foi testada contra NeoForge 21.1.219; pack usa 21.1.248, exigindo smoke/regression sem presumir incompatibilidade.
- **Observações:** API 21.17.7. Battle Styles, Combat Arts e Proficiencies são superfícies oficiais; não tratar como combat engine independente.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/changelogs oficiais Battle Arts API 21.17.7 + Epic Fight 21.17.3.1 físico + NeoForge 21.1.248 físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/battle-arts-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Battle Arts API 21.17.7 físico/release confirmado; Battle Styles/Combat Arts/Proficiencies, schema 21.17.x e boundary Epic Fight preservados. Release testada contra NeoForge 21.1.219; pack usa 21.1.248. QC global #65; runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Battle Arts API 21.17.7 e seus contratos de Battle Styles, Combat Arts e Proficiencies no ecossistema Epic Fight. Em 09/09/2026, a release foi revalidada e o delta entre NeoForge testado 21.1.219 e runtime do pack 21.1.248 foi registrado como gate de QA, sem converter instalação em decisão de manter/remover.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`, mod id `battle_arts_api`, runtime `21.17.7`. O pack também possui Epic Fight `21.17.3.1`; a API pertence ao ecossistema Battle Arts/Epic Fight e não é um combat provider independente.

## 1. Papel e autoridade
Battle Arts API é a camada extensível separada do projeto Battle Arts para permitir que outros mods/datapacks definam **Battle Styles, Combat Arts e Proficiencies**. Ela fornece contratos de integração; **Epic Fight continua authority do pipeline base de combate/animação** quando suas superfícies são usadas.

A API não deve receber dano, stamina ou hit settlement paralelo criado pelo modpack.

## 2. Battle Styles
Battle Styles representam modos/estilos de combate consumidos pelo ecossistema. Uma integração segura deve tratar o style como estado provider-native e não duplicar pose, animation set ou modifiers em um sistema externo.

Mudança de style precisa ser sincronizada e validada pelo servidor quando afeta gameplay.

## 3. Combat Arts
Combat Arts são extensões/ações de combate registráveis por consumidores. Regras de causalidade:
- input do cliente solicita a ação;
- provider valida estado/cooldown/requisitos;
- hit/damage é resolvido uma vez pelo pipeline canônico;
- animação e VFX não podem causar um segundo settlement.

## 4. Proficiencies
Proficiencies são outra superfície declarada da API para especialização/compatibilidade. Elas devem permanecer fonte de dados do provider/consumer; o RPG Skill Tree não deve inventar uma proficiência paralela com o mesmo significado sem bridge explícita.

## 5. Data-driven/API evolution
A linha 21.17.x teve mudança documentada de nomenclatura `slash_modifier` → `damage_attribute_modifier` na 21.17.6. Isso é um risco real para datapacks/consumidores que ficaram presos ao nome antigo. A versão física 21.17.7 deve ser validada com dados escritos para a mesma família de schema.

## 6. Relação com Epic Fight
O pack usa Epic Fight 21.17.3.1. A release física Battle Arts API 21.17.7 declara teste contra NeoForge 21.1.219; o pack atual usa NeoForge 21.1.248. Isso não prova incompatibilidade, mas exige smoke/regression no ambiente real. Battle Arts API não substitui:
- attack animation state;
- hit detection;
- damage settlement;
- entity combat capability/state;
- synchronization base do Epic Fight.

Qualquer Combat Art que interfira nesses pontos deve utilizar hooks do provider, não reexecutar ataque vanilla em paralelo.

## 7. Estado físico do ecossistema no pack
A busca física confirma Battle Arts API e Epic Fight. Um top-level `Battle Arts` separado não foi confirmado nesta verificação específica; portanto a necessidade da API deve ser rastreada por seus consumidores reais, não assumida apenas pelo nome.

## 8. Client/server e multiplayer
- style/proficiency/eligibility/cooldown e hit settlement são servidor quando afetam gameplay;
- input, HUD e animação são cliente/apresentação;
- troca de arma/style durante animação precisa resultar em uma única state transition válida;
- dois packets ou animation callbacks não podem executar a mesma art duas vezes.

## 9. Lifecycle
Validar login/relogin, death/respawn, dimension change, equip/unequip, troca de battle style, reload de datapack e reconnect durante Combat Art. State persistente precisa usar owner correto e limpar referências de entidade/arma inválidas.

## 10. Riscos
1. Double-hit/double-damage ao combinar callback da art com pipeline Epic Fight.
2. Datapack usando chave antiga de modifier.
3. Style/proficiency desync cliente-servidor.
4. Consumidor assumindo versão de Epic Fight diferente da física.
5. Animation event virando authority de gameplay.
6. API instalada sem consumidor real ser confundida com conteúdo jogável.

## 11. Matriz de testes
1. Dedicated server boot com Epic Fight 21.17.3.1.
2. Datapack/schema de 21.17.7 sem chaves obsoletas.
3. Troca de Battle Style em multiplayer.
4. Combat Art executa exatamente um hit/settlement por janela válida.
5. Cancelamento, weapon swap, death e dimension change durante art.
6. Proficiency/state não persiste no jogador errado após respawn/reconnect.

## 12. Evidência
- modlist física atual: Battle Arts API 21.17.7 e Epic Fight 21.17.3.1;
- CurseForge oficial Battle Arts API;
- descrição oficial das superfícies Battle Styles, Combat Arts e Proficiencies;
- changelog 21.17.6 da linha atual registrando a migração de modifier.

> ⚔️ Ownership canônico: Battle Arts API = contratos/extensões; Epic Fight = pipeline base de combate. O catálogo não promove a API a segundo combat engine.
