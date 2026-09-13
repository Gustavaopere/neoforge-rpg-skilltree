# Paladin Spells - Iron's Spells Addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c8a877c6442fc712c1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `paladin_spells-1.21.1-1.1.1.jar`, mod id `paladin_spells`, runtime `1.21.1-1.1.1`; Iron's Spells `3.16.3` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Paladin Spells 1.1.1, Iron's 3.16.3 e Patchouli 93 estão presentes; Patchouli permanece fora das hard dependencies atuais conforme a lineage documentada.

## Propriedades do banco

- **Mod:** Paladin Spells - Iron's Spells Addon
- **Arquivo JAR:** `paladin_spells-1.21.1-1.1.1.jar`
- **Versão 1.21.1:** 1.21.1-1.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells focado em magia Holy de paladino/tank, com Bulwark, Taunt, Sworn Protector, Bedrock Skin e Ram para armor scaling, aggro, proteção e mitigação.
- **Dependências:** Iron's Spells 'n Spellbooks — provider/base confirmado no pack físico (3.16.3). Patchouli não é hard dependency atual; a lineage 0.342 removeu essa dependência desnecessária.
- **Sobreposição:** Temática com outros addons de Iron's, mas o nicho tank/Holy, taunt, damage redirect e armor scaling é específico; avaliar redundância por spell concreto.
- **Compatibilidade/Riscos:** Riscos: armor/Holy scaling, modifier lifecycle, aggro de AI customizada, damage redirect, movement conflict e dedicated-server classloading. 1.1.1 corrige crash de servidor por client-only class, duração de Sworn Protector e cooldown de Bulwark.
- **Observações:** Runtime 1.21.1-1.1.1, file ID 8661566, Release 16/08/2026. Bulwark cooldown 45 s na linha 1.21 após fix 1.1.1. Armadura Paladin 3D mencionada como desenvolvimento não foi tratada como conteúdo instalado.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 1.1.1 e changelogs 0.33/0.342/1.1.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/paladin-spells-irons-spells-n-spellbooks-addon
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Paladin Spells 1.1.1 reconstruído: cinco spells Holy/tank, scaling, classloading fix, lifecycle, compatibilidade, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `paladin_spells-1.21.1-1.1.1.jar`, mod id `paladin_spells`, versão `1.21.1-1.1.1`, NeoForge 1.21.1. É addon de Iron's Spells 'n Spellbooks voltado a magia de **paladino/tank**, com cinco spells publicados que escalam com Holy spell power. A release 1.1.1 corrige duração, cooldown e um crash de servidor por carregamento de classe client-only.

## 1. Identidade e papel
- **Mod:** Paladin Spells - Iron's Spells Addon.
- **JAR físico:** `paladin_spells-1.21.1-1.1.1.jar`.
- **Mod id:** `paladin_spells`.
- **Runtime:** `1.21.1-1.1.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença publicada:** CC BY-NC-SA 4.0.
- **Papel:** adicionar spells Holy orientados a tanking, proteção, controle de aggro e mitigação sobre o framework de Iron's Spells 'n Spellbooks.
- **Decisão:** Sem decisão.

## 2. Dependência e ownership
O pack físico contém `irons_spellbooks-1.21.1-3.16.3.jar`. Iron's continua authority de:
- casting, mana e cooldowns;
- spell registry/schools;
- Holy spell power e demais atributos mágicos base;
- spellbook/caster infrastructure.

Paladin Spells é authority apenas dos spells e regras adicionais que registra sobre esse framework.

## 3. Modelo de escala Holy/tank
A documentação oficial afirma que os spells do addon **escalam com Holy spell power**. O design não é apenas “mais dano”: ele converte poder Holy e, em alguns casos, armor atual em ferramentas defensivas.

No pack, isso cruza gear, atributos e outros addons de Iron's. Balanceamento deve separar:
- armor base do jogador;
- Holy spell power;
- bônus globais de atributos/perks;
- mitigation/redirect do spell.

## 4. Bulwark
**Bulwark** aumenta armor com base na armor atual e spell power conforme a descrição oficial. Isso pode produzir scaling multiplicativo/composto em builds de tank.

A release 1.1.1 corrige o cooldown da versão 1.21 para **45 segundos** e também reduz duração em relação ao estado anterior.

Regression gates:
- modifier aplicado uma vez;
- removal ao fim do spell;
- equip/unequip durante o efeito;
- relog/death sem modifier órfão.

## 5. Taunt
**Taunt** força inimigos dentro de um raio a mirar o caster. A lineage 0.33 corrigiu o spell para funcionar também com hostis que **não estendem `Monster`**, citando mobs de Cataclysm como exemplo.

Isso é relevante ao pack, que contém grandes mob/boss mods. Testar por entidade concreta; não assumir cobertura universal de qualquer AI customizada.

## 6. Sworn Protector
**Sworn Protector** redireciona ao caster uma porcentagem do dano recebido por jogadores próximos, segundo a documentação pública.

A 1.1.1 corrige uma duração incorreta na linha 1.21; a lineage 0.33 também registrou correção do trigger.

Superfícies críticas:
- damage source attribution;
- redirect exatamente uma vez;
- caster morto/desconectado;
- múltiplos protetores próximos;
- dano que ignora mitigation ou usa pipeline customizado.

## 7. Bedrock Skin
**Bedrock Skin** imobiliza o caster em troca de mitigação percentual de dano.

É um hard defensive state: movimento precisa ficar bloqueado apenas durante a duração válida e ser restaurado de forma confiável após fim, dispel, death, dimension change ou reconnect.

Não confundir a imobilização deste spell com stun/root de outros mods.

## 8. Ram
**Ram** é um dash curto através de inimigos cujo dano escala com armor, segundo a descrição oficial.

Interseções no pack:
- Epic Fight e outros sistemas de movimento/combate;
- collision/hit detection;
- knockback;
- armor modifiers temporários, incluindo Bulwark.

Testar se armor é amostrada uma vez ou dinamicamente sem presumir fórmula interna não publicada.

## 9. Release 1.1.1 — correções exatas
O changelog 1.1.1 registra para a linha 1.21:
- correção da duração de Sworn Protector;
- correção do cooldown de Bulwark para 45 s;
- correção de **server crash causado pelo carregamento de uma classe client-only**;
- remoção do status WIP de dois spells;
- nerf de duração de Bulwark.

Esse crash é um gate obrigatório de dedicated server: a build atual deve carregar sem path de client class no servidor.

## 10. Patchouli não é dependency atual
A lineage 0.342 removeu uma dependency desnecessária de Patchouli. Portanto Patchouli não deve ser listado como hard dependency deste addon apenas por versões antigas ou documentação do ecossistema.

## 11. Client/server e multiplayer
Server-authoritative:
- spell cast válido;
- mana/cooldown;
- armor modifiers;
- target selection/taunt;
- damage redirect/mitigation;
- dash hit/damage.

Client-facing:
- particles, animation e feedback visual.

O servidor deve iniciar e executar os spells sem carregar classes exclusivas de cliente — justamente a regressão corrigida em 1.1.1.

## 12. Sobreposição no stack mágico
Há vários addons de Iron's no pack. Paladin Spells possui nicho relativamente específico: tanking Holy, aggro e proteção de grupo.

Sobreposição deve ser avaliada por efeito concreto, por exemplo outro taunt, damage redirect ou armor steroid. “Outro addon de Iron's” não é redundância suficiente.

A página do projeto também menciona armadura Paladin 3D em desenvolvimento; **roadmap não é conteúdo instalado** e não foi promovido a feature desta ficha.

## 13. Riscos
1. **Armor/Holy scaling:** power creep em builds com armor alta.
2. **Modifier lifecycle:** Bulwark não pode duplicar ou ficar preso.
3. **Aggro AI compatibility:** Taunt pode não dominar goals customizados de todo boss.
4. **Damage redirect recursion:** Sworn Protector deve evitar loops/double application.
5. **Movement conflict:** Bedrock Skin/Ram cruzam sistemas de movimento/combate.
6. **Dedicated-server classloading:** regressão explicitamente corrigida em 1.1.1.
7. **Multiple protectors:** stacking/priority precisa ser testado.
8. **Addon overlap:** outros spells Holy/tank podem comprimir a progressão.

## 14. Matriz de testes
- [ ] Dedicated server inicia com Paladin Spells 1.1.1 + Iron's 3.16.3 sem client-class crash.
- [ ] Bulwark aplica/remova armor uma vez e respeita cooldown de 45 s.
- [ ] Bulwark com troca de gear não duplica modifier.
- [ ] Taunt força hostis vanilla e uma amostra de mobs customizados compatíveis a focar o caster.
- [ ] Sworn Protector redireciona dano uma vez e limpa state após caster sair/morrer.
- [ ] Dois protetores próximos não criam loop de redirect.
- [ ] Bedrock Skin bloqueia movimento apenas durante o spell e libera após todos os lifecycle exits.
- [ ] Ram executa dash/hit sem double-hit com Epic Fight ou outros combat hooks.
- [ ] Death/relog/dimension change limpam efeitos/modifiers temporários.
- [ ] Holy spell power alto não ultrapassa a curva pretendida sem revisão de balanceamento.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: `paladin_spells-1.21.1-1.1.1.jar`, mod id/runtime exatos.
- CurseForge oficial: project 1593269, file ID 8661566, Release NeoForge 1.21.1 de 16/08/2026, Client & Server.
- Página oficial: Bulwark, Taunt, Sworn Protector, Bedrock Skin e Ram, todos no eixo Holy/tank.
- Changelog 1.1.1: fixes de duração/cooldown, server classloading e Bulwark; lineage 0.33/0.342: Taunt/Protector e remoção de Patchouli dependency.
- **Limite:** fórmulas numéricas internas, raio, porcentagens e registry IDs não publicados não foram inventados; extrair do JAR/config antes de scripting/balanceamento fino.
