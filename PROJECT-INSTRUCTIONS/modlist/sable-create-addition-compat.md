# Sable Create Addition Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c690c0d528fd7e510d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable_createaddition_compat-0.1.13.jar`, mod id `sable_createaddition_compat`, runtime `0.1.13`, mixin `sable_createaddition_compat.mixins.json`; Sable 2.0.5, Create 6.0.10 e Create Crafts & Additions 1.7.0 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable Create Addition Compat 0.1.13, Sable 2.0.5, Create 6.0.10 e Create Crafts & Additions 1.7.0 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable Create Addition Compat
- **Arquivo JAR:** `sable_createaddition_compat-0.1.13.jar`
- **Versão 1.21.1:** 0.1.13
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Bridge Create Crafts & Additions ↔ Sable para wires entre world/sublevels e compatibilidade de render/posição de block entities em estruturas físicas.
- **Dependências:** Sable 2.0.5 + Create 6.0.10 + Create Crafts & Additions 1.7.0 estão fisicamente presentes. 0.1.13 removeu hard-pin estrito de versão do Sable, mas não a dependência funcional da bridge.
- **Sobreposição:** Não duplica Create Crafts & Additions; C&A mantém wires/energia/BEs. Não duplica Sable; Sable mantém sublevels/transforms. O mod só coordena a boundary.
- **Compatibilidade/Riscos:** Beta-only. Riscos: coordinate mismatch, ghost/overstretched wires, double energy processing, BE render transform incorreto, Liquid Blaze Burner runtime/preview divergence, unload race e API drift Sable/C&A.
- **Observações:** 0.1.13 adiciona compat geral de block-entity rendering e melhora Accumulator, Motor, Portable Energy Interface, Rolling Mill e Liquid Blaze Burner, incluindo diagram/preview/contraption render path.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial Create Additions - Sable Compat 0.1.13.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-additions-sable-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Sable Create Addition Compat 0.1.13 reconstruído: wires cross-sublevel/world, auto-snap, BE render, Liquid Blaze Burner, lifecycle, ownership, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable_createaddition_compat-0.1.13.jar`, mod id `sable_createaddition_compat`, versão `0.1.13`. Esta bridge adapta **Create Crafts & Additions** ao sistema de sublevels do **Sable**, cobrindo fios entre espaços móveis/world e renderização de block entities específicas. O stack físico atual usa Sable 2.0.5, Create 6.0.10 e Create Crafts & Additions 1.7.0.

## 1. Identidade e papel
- **Mod:** Sable Create Addition Compat.
- **JAR:** `sable_createaddition_compat-0.1.13.jar`.
- **Mod id:** `sable_createaddition_compat`.
- **Versão:** `0.1.13`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Mixin config físico:** `sable_createaddition_compat.mixins.json`.
- **Papel:** bridge Create Crafts & Additions ↔ Sable.

## 2. Providers físicos
O pack contém:
- **Sable 2.0.5** — authority de sublevels/transforms;
- **Create 6.0.10** — base cinética/contraptions;
- **Create Crafts & Additions 1.7.0** — authority de fios, energia e block entities próprias;
- **Sable Create Addition Compat 0.1.13** — boundary de compatibilidade.

A bridge não deve funcionar como substituta de nenhum dos três providers.

## 3. Authority e ownership
- **Create Crafts & Additions:** endpoints/wires, energia, Accumulator, Motor, Portable Energy Interface, Rolling Mill, Liquid Blaze Burner e demais conteúdo C&A.
- **Sable:** posição/orientação e lifecycle dos sublevels.
- **Create:** infraestrutura Create compartilhada.
- **Compat:** transforma/projeta posições e corrige paths de render/conectividade quando os objetos C&A estão no world ou em sublevels.

## 4. Fios world ↔ sublevel
A descrição oficial registra que wires C&A podem conectar entre:
- physics-object/sublevel e world normal;
- dois sublevels diferentes.

O problema central é que endpoints em espaços distintos não podem ser comparados/renderizados usando coordenadas locais brutas. A bridge precisa resolver a posição global atual de cada endpoint.

## 5. Snap de fio por movimento
O projeto documenta que wires **se rompem automaticamente se o movimento os esticar demais**.

Isso é importante porque a distância entre endpoints muda continuamente quando um sublevel se move. O estado funcional do wire não pode continuar conectado indefinidamente apenas porque os endpoints eram válidos no momento da criação.

## 6. Render de wires
Além da conectividade, a bridge ajusta o rendering dos wires nos cenários cross-space. Regression gates:
- endpoint world ↔ sublevel;
- sublevel A ↔ sublevel B;
- movimento e rotação;
- camera/render distance;
- unload/reload de um dos espaços.

Wire visualmente correto não prova que a conexão funcional/energética está correta; ambos devem ser testados.

## 7. Delta exato da 0.1.13
O changelog oficial da build instalada registra:
- compatibilidade geral de render de **Create Crafts & Additions block entities** em Sable sublevels;
- melhorias específicas para **Accumulator**, **Motor**, **Portable Energy Interface**, **Rolling Mill** e BEs similares;
- fix dedicado para **Liquid Blaze Burner**;
- patch do path de **diagram / preview / contraption render** do Liquid Blaze Burner;
- atualização para versões mais novas de Sable;
- remoção do hard-pin estrito de versão do Sable.

Esses itens são regression gates diretos da 0.1.13.

## 8. Block entities C&A em sublevels
Block entities continuam pertencendo ao provider C&A, mas seu renderer e algumas consultas de posição podem falhar quando executadas em plot/local coordinates.

A bridge deve corrigir o espaço de referência sem:
- duplicar tick funcional;
- criar BE paralela;
- alterar energia duas vezes;
- serializar um segundo state fora do provider.

## 9. Portable Energy Interface
PEI é uma boundary particularmente sensível por combinar energia/transferência com estruturas móveis. O compat deve assegurar que render/posição e conexão sejam coerentes quando um lado ou ambos estão em sublevel.

Não foi inferida uma nova lógica de transferência de energia do compat: Create Crafts & Additions continua authority da energia e da interface.

## 10. Liquid Blaze Burner
A 0.1.13 contém fix específico de Liquid Blaze Burner, inclusive em preview/diagram/contraption render path. Isso sugere superfície tanto de runtime quanto de visualização.

Testar o bloco em world, sublevel e contraption, distinguindo:
- state funcional;
- render normal;
- preview/diagram/Ponder-like path quando aplicável.

## 11. Client / Server
Conectividade funcional, existência do wire e transferências devem convergir no servidor/provider. Render de wires/BEs/preview é client-facing.

Uma correção de render não pode criar conexão funcional apenas no cliente; uma conexão válida no servidor não pode permanecer invisível ou desenhada em coordenadas erradas indefinidamente.

## 12. Lifecycle
Validar:
- criação do wire com endpoints estáticos;
- assembly do sublevel após wire existir;
- wire criado entre world/sublevel já montado;
- wire entre dois sublevels;
- movimento/rotação e over-stretch;
- disassembly/destruction;
- chunk/sublevel unload/reload;
- server restart;
- endpoint block quebrado;
- update/reload de provider.

## 13. Provider C&A 1.7.0
O pack usa Create Crafts & Additions 1.7.0. Essa build está catalogada separadamente e sua maturidade deve ser tratada na página do provider.

Para esta bridge, qualquer alteração/downgrade do provider exige revalidar exatamente:
- wire endpoints;
- Accumulator;
- Motor;
- PEI;
- Rolling Mill;
- Liquid Blaze Burner.

## 14. Riscos técnicos
1. **Coordinate mismatch:** endpoint usa local coords em vez de world transform.
2. **Ghost wire:** conexão permanece após endpoint/sublevel desaparecer.
3. **Over-stretch failure:** wire não rompe ou rompe cedo demais.
4. **Double energy path:** bridge e provider processam transferência separadamente.
5. **BE render transform:** Accumulator/Motor/etc. aparecem deslocados/rotacionados incorretamente.
6. **Liquid Blaze Burner divergence:** preview correto, runtime errado ou vice-versa.
7. **Sable API drift:** transform/sublevel API muda.
8. **C&A API drift:** wire/renderer/BE internals mudam.
9. **Unload race:** um endpoint descarrega durante cálculo de posição/conexão.

## 15. Matriz de testes
- [ ] Dedicated server inicia com compat 0.1.13 + Sable 2.0.5 + Create 6.0.10 + C&A 1.7.0.
- [ ] Wire world↔sublevel conecta e transfere conforme provider.
- [ ] Wire sublevel↔sublevel conecta com poses diferentes.
- [ ] Wire renderiza nos endpoints corretos durante movimento/rotação.
- [ ] Movimento além do limite provoca snap uma única vez e remove state corretamente.
- [ ] Break de endpoint remove conexão sem ghost wire.
- [ ] Accumulator/Motor/PEI/Rolling Mill renderizam corretamente em sublevel.
- [ ] PEI não duplica transferência/tick ao cruzar espaços móveis.
- [ ] Liquid Blaze Burner funciona/renderiza em runtime e preview/diagram.
- [ ] Unload/reload e server restart preservam apenas conexões válidas.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física canônica de 10/09/2026: versões exatas de Sable, Create, C&A e compat.
- CurseForge oficial 0.1.13: changelog de BE rendering/Liquid Blaze Burner/newer Sable e remoção de hard-pin.
- Descrição oficial: wires world↔sublevel, sublevel↔sublevel, render ajustado e auto-snap por estiramento.
- **Limite:** thresholds de wire, cálculo energético e classes internas não foram inventados; pertencem ao provider/build efetiva.
