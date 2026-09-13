# Ars Creo

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8185a05bfb6243e28f2f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Creo
- **Arquivo JAR:** `ars_creo-1.21.1-5.4.0.jar`
- **Versão 1.21.1:** 5.4.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Tecnologia, Automação, Compat
- **Função:** Bridge Ars Nouveau↔Create: 8 blocos/8 block entities próprios, Starbuncle Wheel/cinética, Source Motor/Gearbox/converters e behaviors que tornam turrets, Source Jars, portals e rituals compatíveis com contraptions Create.
- **Dependências:** Ars Nouveau 5.13.1 + Create 6.0.10. Ars mantém authority de Source/spells; Create mantém authority de stress/speed/contraptions.
- **Sobreposição:** Complementa Ars Technica/Applied Create e outras bridges, mas seu domínio é especificamente Ars Nouveau dentro de contraptions e conversões Source/cinética. Evitar dupla conversão/processamento.
- **Compatibilidade/Riscos:** Riscos: double cast em assembly/disassembly, duplicação de Source em serialization de contraption, stress amplification, coordenadas móveis de turret/portal, ritual replay e lifecycle durante unload/restart. Não inferir Sable compatibility a partir de Create compatibility.
- **Observações:** mod id `ars_creo`. Build 5.4.0: 8 blocos funcionais + 8 block entity types; Display Sources `turret` e `source_jar`; movement behavior explícito para turrets, jars, portal e ritual.
- **Procedência:** modlist.txt física atual de 08/09/2026 + source oficial baileyholl/Ars-Creo 5.4.0 + dossiê operacional existente.
- **Fonte:** https://github.com/baileyholl/Ars-Creo
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Ars Creo 5.4.0, 8 blocos/8 BEs, contraption behaviors, Source↔cinética, Starbuncle Wheel e Create authority confirmados no QC global #42. Estado anterior `Integrado ao Github` preservado como histórico documental.
- **Histórico da decisão:** Manter. Ficha reconstruída do zero em 07/09/2026 contra source 5.4.0 e modlist física; bridge é estrutural para technomancy móvel e não substitui Ars Nouveau nem Create.
- **Data da última decisão:** 2026-09-07

> ⚙️ **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_creo-1.21.1-5.4.0.jar`, mod id `ars_creo`, NeoForge 1.21.1. A build 5.4.0 registra **8 blocos funcionais + 8 block entities** e integra diretamente turrets, Source Jars, portals e Ritual Brazier/ritual blocks ao movement system do Create. Create continua authority de contraptions/stress; Ars Nouveau continua authority de Source/spells.

## 1. Identidade e versão
- **Mod:** Ars Creo.
- **JAR:** `ars_creo-1.21.1-5.4.0.jar`.
- **Runtime:** `5.4.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel:** bridge estrutural Ars Nouveau ↔ Create para contraptions, Source e cinética.
- **Decisão:** **Manter**.

## 2. Blocos próprios registrados — 8
1. `starbuncle_wheel` — geração cinética baseada em Starbuncle.
2. `source_motor` — conversão de Source em movimento/capacidade cinética segundo a configuração do addon.
3. `source_gearbox` — gearbox/bridge de Source e infraestrutura cinética.
4. `starbuncle_fluid_sphere` — esfera/tank de fluido ligada à automação Starbuncle.
5. `pressurized_source_jar` — variante de Source Jar para integração pressurizada/fluid-like.
6. `source_fluid_siphon` — conversão/manipulação entre Source e fluido conforme o bloco do addon.
7. `source_jar_correspondent` — correspondent para Source Jar.
8. `fluid_jar_correspondent` — correspondent para fluid jar.

Os oito possuem **block entity types próprios** registrados.

## 3. Integração de contraptions com blocos Ars
O `CreateCompat` da build registra movement/interaction behavior para conteúdo do Ars Nouveau:
- **Basic Spell Turret** — interaction + movement behavior; pode conjurar em contraption sob a interação suportada.
- **Timer Spell Turret** — movement behavior para casts periódicos.
- **Enchanted Spell Turret** — movement behavior para atuação durante movimento/contato conforme o provider.
- **Source Jar** e **Creative Source Jar** — Source acompanha a contraption e permanece acessível aos behaviors suportados.
- **Portal Block** — movement + interaction behavior próprios.
- **Ritual Block** — movement + interaction behavior próprios.

O addon também força checks específicos para permitir movimento dos blocos Portal/Ritual nos casos tratados.

## 4. Display Sources — 2
A integração Create registra duas fontes de Display Link:
1. `turret` — telemetria de Spell Turrets.
2. `source_jar` — telemetria de Source Jars.

Essas display sources são observabilidade; não devem ser usadas como authority de cast ou consumo de Source.

## 5. Starbuncle Wheel
O source registra a wheel como **stress capacity provider** do Create, com valor vindo da config do addon. O guia do projeto registra que o Starbuncle produz rotação e que condições/blocos específicos podem alterar RPM conforme a mecânica da wheel.

Boundary: o Starbuncle/Ars fornece a criatura e o comportamento do addon; **Create é authority de speed/stress**. Não duplicar SU em perk ou bridge paralela.

## 6. Source e cinética
`source_motor`, `source_gearbox`, `pressurized_source_jar`, `source_fluid_siphon` e correspondents formam a camada de conversão/transporte. Regras:
- Source não vira uma segunda moeda quando convertido;
- qualquer conversão deve conservar custo/estado real;
- não publicar output de fluido/rotação antes da operação real;
- reload/chunk unload precisa preservar inventário e reservas sem dupe.

## 7. Turrets móveis
Uma turret em contraption continua usando o **spell real do Ars**, com Source/caster context conforme o behavior do addon. Isso é relevante para perks:
- movimento da contraption não cria novo caster;
- cada cast deve consumir uma vez;
- casts de Timer/Enchanted Turret precisam de causalidade distinta de uma interação direta do jogador;
- automação não implica autoria humana automática para XP/Mastery.

## 8. Portals e rituals móveis
A build possui tratamento explícito para Portal Block e Ritual Block. Isso prova suporte direcionado, mas não significa que qualquer bloco Ars seja contraption-safe. Cada integração adicional deve ser testada pelo behavior real.

## 9. Authority e sobreposição
- **Ars Nouveau:** Source, spellbook/glyphs, turrets, portals, rituals.
- **Create:** contraption, movement, stress/speed, display infrastructure.
- **Ars Creo:** behaviors e blocos que conectam os dois domínios.
- **Ars Technica:** technomancy/spell processing é outro addon; não duplicar seu papel.

## 10. Riscos
1. double cast em assembly/disassembly;
2. Source duplicado em moving block entity serialization;
3. stress amplification por bridge paralela;
4. coordenadas erradas ao mover turret/portal;
5. ritual/portal reexecutado ao reassemble;
6. chunk unload/restart em contraption ativa;
7. interação com Sable/sublevels: não presumir que Create contraption compatibility equivale a Sable compatibility.

## 11. Matriz de validação
- boot client + dedicated server;
- os 8 blocos e 8 block entities;
- Starbuncle Wheel sob carga e após restart;
- Source Motor/Gearbox/converters com conservation check;
- Basic, Timer e Enchanted Spell Turret em contraption;
- Source Jar móvel alimentando turret;
- portal e ritual em assembly/movement/disassembly;
- Display Link `turret` e `source_jar`;
- cancelamento/desmontagem sem dupe;
- performance com múltiplas contraptions mágicas.

## 12. Fontes
- Modlist física do projeto.
- Source oficial `baileyholl/Ars-Creo`, build `5.4.0`.
- Guia consolidado de Magia 07/09/2026.
