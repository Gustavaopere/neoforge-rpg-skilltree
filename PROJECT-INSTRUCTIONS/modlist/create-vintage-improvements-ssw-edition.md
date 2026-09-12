# Create: Vintage Improvements — SSW Edition

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e89a71c3553a990f9d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Vintage Improvements — SSW Edition
- **Arquivo JAR:** `vintageimprovements-1.21.1-0.0.0.7.jar`
- **Versão 1.21.1:** 1.21.1-0.0.0.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação, Metalurgia
- **Função:** Port SSW de Vintage Improvements para Create 6/1.21.1: máquinas e recipe types cinéticos para wires, rods, sheets, springs, compression, centrifugation, vibrating e curving/forming.
- **Dependências:** Create 6.0.10; NeoForge 21.1.228+ conforme release. Pack atual usa NeoForge 21.1.248. Integrações físicas da 0.0.0.7 cobrem Create Aeronautics/Simulated e precisam de QA no stack atual.
- **Sobreposição:** Overlap industrial por operação com outros addons Create/metalurgia; revisar wires/rods/sheets/springs/compression/centrifugation/forming feature-by-feature, não por categoria genérica.
- **Compatibilidade/Riscos:** Port não oficial. Riscos: Create 6/KubeJS schema drift, fluid/inventory dupe em contraptions, multiblock rotation e overlap econômico com outros processadores. 0.0.0.7 corrige machine schemas, Compressor fluids e contraption compat, mas runtime QA continua necessário.
- **Observações:** Mod id `vintageimprovements`, runtime `1.21.1-0.0.0.7`. Não confundir com o projeto original 1.20.1; scripts/recipes devem usar schemas da SSW atual.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial SSW Edition 0.0.0.7 + Guia Tecnologia atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-vintage-improvements-ssw-edition
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; máquinas, KubeJS, fluidos, contraptions e overlap por processo catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

> ⚙️ **ESCOPO CANÔNICO.** Runtime físico: `vintageimprovements-1.21.1-0.0.0.7.jar`, mod id `vintageimprovements`, runtime `1.21.1-0.0.0.7`. Esta é a **SSW Edition**, port não oficial de Vintage Improvements para Create 6 / Minecraft 1.21.1. Create continua authority de cinética/contraptions; Vintage Improvements adiciona máquinas e recipe types industriais próprios.

## 1. Identidade e requisitos
- **Mod:** Create: Vintage Improvements — SSW Edition.
- **Build instalada:** 0.0.0.7 para 1.21.1.
- **Create requerido:** 6.0.10.
- **NeoForge mínimo publicado:** 21.1.228+.
O pack atual satisfaz os requisitos com **Create 6.0.10** e **NeoForge 21.1.248**.

## 2. Papel industrial
O addon adiciona processos cinéticos voltados à fabricação de formas intermediárias de materiais, incluindo **wires, rods, sheets e springs**, além de transformações mecânicas próprias.
Ele amplia a cadeia Create; não cria uma rede elétrica ou solver de automação paralelo.

## 3. Máquinas principais documentadas
O catálogo atual do projeto/guia registra:
- **Compressor** — máquina com tanque interno e modos/processos envolvendo fluidos;
- **Vibrating Table** — inclui recipes como unpacking;
- **Centrifuge** — trabalha em conjunto com Basins em recipes suportados;
- **Curving Press** — usa heads específicos para conformação;
- processos de rods/wires/sheets/springs e outras operações herdadas/adaptadas do Vintage Improvements.
Recipe IDs, velocidades, stress units e tempos exatos não são inferidos aqui sem inspeção de recipes/config do JAR atual.

## 4. Correções específicas da 0.0.0.7
O changelog da SSW 0.0.0.7 documenta correções relevantes para a linha Create 6:
- schemas/integração de machine recipes com **KubeJS**;
- secondary fluid input/output do **Compressor**;
- interação de fluid containers no topo do Compressor;
- correções para máquinas em **Create Aeronautics / Create Simulated contraptions**;
- ajustes de inventories/multiblock rotation/installed components em contraptions físicas.
Isso torna a build diretamente relevante ao stack físico atual, mas ainda exige runtime QA com as versões concretas do pack.

## 5. Authority e boundaries
- **Create:** kinetics, stress, base contraption mechanics.
- **Vintage Improvements:** seus machine blocks, recipe types e transformação industrial específica.
- **Sable/Aeronautics/Simulated:** physics/sublevel/contraption state quando a máquina está em estrutura física.
Mods próprios não devem:
- repetir recipe execution por listener externo;
- conceder output adicional ao detectar conclusão visual;
- manter tank/inventory espelhado;
- recalcular cinética paralela;
- assumir recipe completion apenas por progress/render state.

## 6. KubeJS e datapack integration
A 0.0.0.7 corrige schemas de recipes para KubeJS. Isso significa que scripts podem ser uma superfície válida de configuração/integração, mas o schema exato precisa vir da build atual/documentação, não de exemplos da versão original 1.20.1.
Um script inválido deve falhar como recipe/configuration issue; não compensar criando recipe hardcoded em mod próprio sem necessidade.

## 7. Contraptions físicas
A build declara correções para Create Aeronautics/Create Simulated. O pack possui o stack atual de Aeronautics/Sable/Simulated.
Testes prioritários:
- assembly/disassembly com Compressor/Centrifuge/Curving Press;
- fluid state durante movimento;
- inventory persistence;
- multiblock orientation;
- recipe progress durante chunk/sublevel transitions;
- ausência de dupe ao desmontar máquina com output/tank parcial.

## 8. Sobreposição industrial
O pack já contém diversos sistemas de processamento e metalurgia. A sobreposição deve ser julgada **por processo**, não pelo fato de todos serem “máquinas Create”.
Possíveis overlaps a revisar na curadoria:
- wires/rods/sheets/springs;
- centrifugation/separation;
- compression;
- forming/curving;
- metal processing com Create Metallurgy, Create: Metalwork, Destroy e outros addons atuais.
Não remover recipe type só por existir item final semelhante; considerar progression, automation path e integração física.

## 9. Server authority e lifecycle
Recipe execution, inventories, tanks e outputs são server-authoritative.
Lifecycle crítico:
- server boot/data reload;
- recipe reload via KubeJS/datapack;
- chunk unload/reload;
- machine block break;
- fluid insertion/extraction;
- assembly/disassembly;
- restart com processo parcial;
- multiplayer access concorrente.

## 10. Riscos
1. **Port não oficial:** divergência do upstream original e manutenção dependente da SSW Edition.
2. **Create 6 coupling:** internals/recipes mudaram na migração.
3. **KubeJS schema drift:** scripts antigos podem quebrar ou criar recipes inválidos.
4. **Fluid duplication/loss:** Compressor e machine inventories em contraptions.
5. **Multiblock orientation:** rotação física pode invalidar state.
6. **Overlap econômico:** rotas paralelas podem baratear materiais intermediários.

## 11. Matriz de testes
- [ ] Dedicated server inicia com Vintage 0.0.0.7 + Create 6.0.10.
- [ ] Recipes principais carregam sem schema errors.
- [ ] KubeJS registra/remove recipe de teste usando schema atual.
- [ ] Compressor processa input/output/fluid exactly-once.
- [ ] Fluid container interaction superior funciona sem dupe.
- [ ] Vibrating Table/Centrifuge/Curving Press executam recipes válidos.
- [ ] Save/restart com processo parcial preserva state corretamente.
- [ ] Assemble/move/disassemble em Aeronautics/Simulated preserva inventory/tank.
- [ ] Multiblock rotation não duplica/remove components.
- [ ] Recipe overlaps não criam loop de ganho líquido.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências
- **Modlist física 08/09/2026:** `vintageimprovements-1.21.1-0.0.0.7.jar`, Create 6.0.10, NeoForge 21.1.248.
- CurseForge oficial SSW Edition: port 1.21.1/Create 6, requisitos e changelog 0.0.0.7.
- Guia Tecnologia: Compressor, Vibrating Table, Centrifuge, Curving Press e formas intermediárias.

## 13. Limitação
Não foram enumerados recipe IDs, stress values, processing times ou configs exatos da 0.0.0.7 nesta etapa. Scripts/integrações programáticas devem usar schema/resources da build instalada.