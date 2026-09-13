# Ignis Soulfires: Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ad829cc6e122703411
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Ignis Soulfires: Spellbooks
- **Arquivo JAR:** `ignissoulfires_spellbooks-1.1.0.jar`
- **Versão 1.21.1:** 1.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Magia
- **Função:** Addon de compatibilidade que leva Souled Ignitium de Cataclysm: Ignis Soulfires ao ecossistema mágico de Cataclysm: Spellbooks/Iron's, adicionando um conjunto Souled Ignitium Wizard Armor.
- **Dependências:** Obrigatórias oficiais e presentes: Ignis Soulfires 1.8.0; L_Ender's Cataclysm 3.33; Lionfish API 3.1; AzureLib 3.1.11; Iron's Spells 'n Spellbooks 3.16.3; Cataclysm: Spellbooks 1.1.13-1.21.
- **Sobreposição:** Bridge específica de equipamento mágico; não substitui Ignis Soulfires, Cataclysm Spellbooks ou Iron's. Não assumir que todo Souled Ignitium armor/effect do mod-base é duplicado nesta variante wizard.
- **Compatibilidade/Riscos:** Riscos: ABI drift entre seis providers, atributos mágicos/armor modifiers duplicados, material Souled Ignitium tratado por duas authorities, render/animation drift e update unilateral de Ignis Soulfires/Cataclysm Spellbooks/Iron's.
- **Observações:** A antiga pendência de provenance 1.1.0 foi resolvida: CurseForge oficial mostra 1.1.0 como main release 1.21.1. O conteúdo publicamente confirmado é o Souled Ignitium Wizard Armor; stats, registry IDs e efeitos exatos não publicados permanecem fail-closed.
- **Procedência:** modlist.txt física atual + CurseForge oficial Ignis Soulfires: Spellbooks 1.1.0, Release NeoForge 1.21.1 de 11/08/2026 + descrição oficial do Souled Ignitium Wizard Armor e dependências; source exato 1.1.0 não localizado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ignis-soulfires-spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Ignis Soulfires: Spellbooks 1.1.0 release-pinned; Souled Ignitium Wizard Armor bridge, seis dependencies, equipment/spell authority, lifecycle/multiplayer, riscos e testes catalogados; source exato não localizado.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ignissoulfires_spellbooks-1.1.0.jar`, mod id `ignissoulfires_spellbooks`, versão `1.1.0`. O CurseForge oficial confirma **Ignis Soulfires: Spellbooks 1.1.0** como release NeoForge 1.21.1 de 11/08/2026. A antiga pendência de provenance foi resolvida; source code exato não foi localizado.

## 1. Papel e authority
É uma pequena bridge entre **Cataclysm: Ignis Soulfires** e **Cataclysm: Spellbooks/Iron's Spells 'n Spellbooks**. O conteúdo oficialmente confirmado é um **Souled Ignitium Wizard Armor set** voltado a spellcasters. Ignis Soulfires continua owner do material/propriedades-base de Souled Ignitium; Cataclysm Spellbooks/Iron's continuam owners do framework mágico; este addon é owner da variante integrada que registra.

## 2. Dependências oficiais e snapshot físico
A página oficial exige Ignis Soulfires, Cataclysm, Lionfish API, AzureLib, Iron's Spellbooks e Cataclysm Spellbooks. Todos estão presentes: Ignis Soulfires 1.8.0, L_Ender's Cataclysm 3.33, Lionfish API 3.1, AzureLib 3.1.11, Iron's Spells 3.16.3 e Cataclysm: Spellbooks 1.1.13-1.21.

## 3. Conteúdo confirmado
O upstream publica apenas um escopo funcional concreto para 1.1.0: **Souled Ignitium Wizard Armor**, uma variante mágica da armadura Souled Ignitium compatível com Cataclysm Spellbooks. Não foram publicados nesta fonte registry IDs, números de armor, spell power, mana, cooldown ou efeitos por peça; esses detalhes permanecem fail-closed.

## 4. Boundary de equipamento e atributos
A bridge pode combinar propriedades de material/armor do ecossistema Ignis Soulfires com atributos esperados por spellcasting. Qualquer bonus deve ser aplicado exatamente uma vez pelo provider real. Mods próprios não devem reaplicar spell power, resistance, armor ou set bonus apenas por reconhecer Souled Ignitium no nome.

## 5. Material authority
Souled Ignitium já pertence a Ignis Soulfires 1.8.0. Este addon não deve criar uma segunda ledger do material; recipes, tags e upgrades da variante wizard precisam referenciar o provider real. Atualização de Ignis Soulfires pode quebrar ingredient/tag/attribute contracts mesmo se esta bridge continuar carregando.

## 6. Client / server
A distribuição oficial é Client & Server. Servidor deve ser authority de equip, attributes, damage/resistance, mana/spell modifiers e crafting/result state. Cliente apresenta armor model, textures, tooltips e efeitos visuais. Render não deve ser usado como fonte de gameplay state.

## 7. Lifecycle e multiplayer
Validar registry sync, crafting/obtenção, equip/unequip, armor swap, spell cast, damage intake, death/respawn, reconnect e server restart. Dois jogadores com o mesmo set não podem compartilhar modifier/state. Equipar e desequipar repetidamente não pode acumular atributos.

## 8. Compatibilidade e version drift
Esta bridge depende simultaneamente de seis projetos. O risco principal é ABI/data drift quando qualquer provider altera item IDs, armor APIs ou spell attributes. Atualizações de Ignis Soulfires, Cataclysm Spellbooks ou Iron's devem ser testadas em conjunto antes de entrar no pack principal.

## 9. Riscos técnicos
- ingredient/tag de Souled Ignitium mudar no provider-base;
- armor/spell modifier duplicado em equip/relog;
- Cataclysm Spellbooks ou Iron's alterar atributo/API esperada;
- AzureLib/render layer mudar e quebrar aparência;
- registry mismatch client/server;
- assumir stats/efeitos não publicados para a 1.1.0;
- atualizar um dos seis providers isoladamente.

## 10. Matriz de testes obrigatória
- [ ] Dedicated server boot com a bridge 1.1.0 e seis dependencies físicas atuais.
- [ ] Cliente conecta sem missing registry/linkage error.
- [ ] Todas as peças do Souled Ignitium Wizard Armor podem ser obtidas/equipadas.
- [ ] Equip/unequip/relog não duplica modifiers.
- [ ] Spellcasting com set parcial/completo aplica apenas efeitos documentados pelo runtime.
- [ ] Armor/damage/resistance permanecem server-authoritative.
- [ ] Death/respawn limpa/restaura modifiers corretamente.
- [ ] Resource reload não quebra model/texture/tooltips.
- [ ] Update de Ignis Soulfires/Cataclysm Spellbooks/Iron's é bloqueado até regression test conjunto.

## 11. Evidências e limites
- **Modlist física:** filename, mod id e runtime 1.1.0.
- **CurseForge oficial:** main release 1.1.0, NeoForge 1.21.1, Client & Server, Souled Ignitium Wizard Armor e seis requirements.
- **Limite:** source code exato e registry/stat tables da 1.1.0 não foram localizados; não foram inferidos a partir de 1.0.0.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
