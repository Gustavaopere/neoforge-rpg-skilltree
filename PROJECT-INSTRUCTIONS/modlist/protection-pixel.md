# Protection Pixel

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8107a674f1c4eb608df8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `protection_pixel-2.2.1-neoforge-1.21.1.jar`, mod id `protection_pixel`, runtime `2.2.1`; Create, Epic Fight e ParCool presentes no snapshot físico
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Protection Pixel 2.2.1 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Protection Pixel
- **Arquivo JAR:** `protection_pixel-2.2.1-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Tecnologia
- **Função:** Adiciona armaduras e equipamentos steampunk integrados a Create, com funcionalidades por peça e crafting/processamento temático.
- **Dependências:** Create e dependências declaradas pelo projeto; NeoForge 1.21.1. Epic Fight e ParCool! estão presentes como superfícies de compatibilidade/teste, não hard dependencies assumidas.
- **Sobreposição:** Sobreposição de equipamento/stats com outros RPG mods, mas identidade Create-steampunk e mecânicas próprias de reactor/plataforma/plates.
- **Compatibilidade/Riscos:** Riscos: attribute accumulation após death, modifier stacking com RPG stack, Epic Fight/ParCool composition, heavy-armor support stale state, reactor/fuel accounting e armor plate persistence. MCreator não é critério técnico de remoção.
- **Observações:** Runtime 2.2.1, Release NeoForge 1.21.1 de 30/01/2026. Delta exato: fix de armor value increase after death.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Create: Protection Pixel 2.2.1 + documentação oficial de equipamentos/sistemas.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/protection-pixel
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Protection Pixel 2.2.1 reconstruído: armaduras/grades, reactor/heavy armor, Armor Load Platform/plates, Epic Fight/ParCool composition, death regression, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `protection_pixel-2.2.1-neoforge-1.21.1.jar`, mod id `protection_pixel`, versão `2.2.1`, NeoForge 1.21.1. Create: Protection Pixel adiciona armaduras/equipamentos steampunk com funções próprias, incluindo peças pesadas dependentes de suporte energético. O fato de o JAR ter sido produzido com MCreator não é critério técnico de remoção.

## 1. Identidade e papel
- **Mod:** Create: Protection Pixel.
- **JAR:** `protection_pixel-2.2.1-neoforge-1.21.1.jar`.
- **Mod id:** `protection_pixel`.
- **Runtime:** `2.2.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Release:** estável para 1.21.1.
- **Licença:** Academic Free License v3.0.
- **Papel:** equipamento/armadura Create-steampunk com habilidades por peça e infraestrutura de suporte.
- **Decisão:** Sem decisão.

## 2. Design por peça, não set bonus obrigatório
A descrição oficial enfatiza peças com funções individuais e grades brass/alloy, permitindo combinar peças. Não tratar o conteúdo como um único set monolítico nem inventar set bonus não publicado.

## 3. Helmets
Exemplos publicados:
- **Plague Helmet:** vapor para remover temporariamente Blindness, Darkness, Weakness e Slowness;
- **Lancer Helmet:** relaciona velocidade a maior potencial de dano;
- **Hunter Helmet:** sensores/caça, destacando criaturas em determinadas condições e oferecendo função temática de busca/luck.

Cada efeito precisa ser testado contra atributos/effects reais do servidor.

## 4. Chestplates
Exemplos incluem:
- **Breaker Chestplate:** altera dano/attack speed mantendo proteção;
- **Magnetic Storm Chestplate:** ataques/efeitos magnéticos ao redor do usuário sob condições publicadas de dano/alvos;
- **Float Shield Chestplate:** camada defensiva/protective shell.

Não inferir números de stats sem config/runtime.

## 5. Heavy machinery armor e reactor
Armaduras pesadas dependem de assistência externa. A documentação descreve um **combustion reactor** que ferve água usando flare rods e alimenta funções da armadura; sem suporte, peças podem perder funções e impor Slowness, Mining Fatigue e Weakness.

Isso cria lifecycle entre equipamento vestido, combustível, água e state do reactor.

## 6. Água, flare rods e carga
O sistema usa water tanks e flare rods; a documentação relaciona quantidade de rods à capacidade de suportar peso/armadura. Testar troca de armor, falta de água/combustível e reconexão sem buffs/debuffs stale.

## 7. Armor Load Platform e armor plates
A página publica uma plataforma de carregamento/configuração acessada por menu, capaz de instalar/desmontar armor plates. O processo usa rotação mecânica e consome lava; a plataforma possui armazenamento próprio de fluido.

Armor plates alteram stats e podem reparar/ajustar equipamento. Validar consumo atômico de material/fluido e persistência dos upgrades.

## 8. Integração com Create
A identidade visual/mecânica depende do ecossistema Create: rotação, máquinas/plataforma e crafting/processamento temático. Create continua owner de kinetic network; Protection Pixel é owner dos equipamentos e devices deste addon.

## 9. Epic Fight e ParCool
O autor informa testes com Epic Fight, Better Combat e Parcool. No pack atual estão confirmados **Epic Fight** e **ParCool!**.

Isso é evidência de intenção/compatibility testing upstream, não garantia absoluta para as versões exatas atuais. Testar armature, movement, attack speed e habilidades enquanto battle/parkour systems estão ativos.

## 10. Release 2.2.1
A build instalada é Release NeoForge 1.21.1 publicada em 30/01/2026.

Delta exato publicado: **fix de aumento do armor value após morte**.

Esse bug é um regression gate crítico porque envolve persistência/acúmulo de atributo através de death/respawn.

## 11. Atributos e stack RPG
O pack possui Apotheosis/Apothic, Relics, Iron's e outros providers de atributos/equipamento. Riscos:
- modifier duplicado após equip/death;
- attack speed/damage stack fora da curva;
- armor/toughness acima do esperado;
- buffs temporários persistindo após remover peça.

Ownership deve ser identificado pelo modifier source antes de culpar o mod.

## 12. Client/server e persistência
Stats, buffs/debuffs, consumo de combustível e upgrades precisam ser server-authoritative. Render/model é client-facing.

Persistência crítica: armor plates, reactor support, death/respawn, dimension change, relog e troca rápida de equipamento.

## 13. Riscos
1. **Attribute accumulation:** regressão explicitamente corrigida em 2.2.1.
2. **Epic Fight/ParCool composition:** pose, attack speed e movement podem competir.
3. **Heavy armor stale state:** debuff/buff fica após tirar armadura ou perder suporte.
4. **Fuel/fluid accounting:** plataforma/reactor duplica ou consome errado.
5. **Armor plate persistence:** upgrade se perde/duplica.
6. **RPG stat stacking:** outros providers ampliam valores.
7. **Client render vs server stats:** aparência correta não prova modifier correto.

## 14. Matriz de testes
- [ ] Dedicated server e cliente iniciam com 2.2.1 + Create.
- [ ] Equip/unequip de cada peça representativa adiciona/remove modifiers uma vez.
- [ ] Death/respawn não aumenta armor value cumulativamente.
- [ ] Plague Helmet remove somente effects publicados nas condições esperadas.
- [ ] Lancer/Breaker interagem com velocidade/attack speed sem modifier stale.
- [ ] Heavy armor com/sem reactor aplica/remove suporte/debuffs corretamente.
- [ ] Water/flare rod consumption persiste após relog.
- [ ] Armor Load Platform consome lava/rotação e grava plate uma vez.
- [ ] Epic Fight battle mode mantém stats/poses coerentes.
- [ ] ParCool movement não deixa habilidade/armor state inconsistente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: JAR, mod id/runtime, marcação MCreator e mixin config.
- CurseForge oficial: project 1023409, Release 2.2.1 NeoForge 1.21.1 de 30/01/2026, AFL-3.0.
- Changelog 2.2.1: fix de armor value aumentando após death.
- Descrição oficial: peças/grades, reactor, water/flare rods, Armor Load Platform/plates e compat tests citados.
- **Limite:** valores numéricos de stats, recipes e configs locais não foram inventados; Better Combat não foi tratado como presente sem confirmação física.
