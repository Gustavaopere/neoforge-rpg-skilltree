# Excalibur | Eidolon Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db815a86e2e2505697389f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Eidolon_1.21.1_v1.zip`
- **Versão 1.21.1:** v1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Eidolon_1.21.1_v1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `eidolon_repraised-1.21.1-0.5.0.2.jar`, mod id `eidolon_repraised`, runtime `0.5.0.2`.
- A cobertura publicada é deliberadamente limitada: todos os mobs, mais uma purple gem. Isso não é convertido em overhaul completo de Eidolon.
- O snapshot top-level auditado não identifica uma entrada separada de Eidolon:Edoni; a menção upstream permanece relacionada/opcional, não dependência física assumida.

## Propriedades do banco

- **Mod:** Excalibur | Eidolon Support
- **Arquivo JAR:** `Excalibur_Eidolon_1.21.1_v1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** v1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Magia
- **Função:** Compatibility resource pack que retexturiza todos os mobs de Eidolon:Repraised para o estilo Excalibur; a v1 também inclui uma purple gem e não pretende cobrir o mod inteiro.
- **Dependências:** Uso visual pretendido: Excalibur + Eidolon:Repraised. Stack físico atual: Eidolon:Repraised 0.5.0.2. Eidolon:Edoni é citado upstream como relacionado/opcional, mas não está instalado.
- **Sobreposição:** Pode colidir com outros retextures/model packs dos mesmos mobs. Demais assets de Eidolon fora do escopo não devem ser atribuídos a este support pack.
- **Compatibilidade/Riscos:** Cobertura deliberadamente limitada a mobs + uma purple gem. Riscos de drift com Eidolon:Repraised 0.5.0.2, load order e colisão com outros mob/model packs. Não tratar Eidolon:Edoni como dependência física.
- **Observações:** Arquivo instalado `Excalibur_Eidolon_1.21.1_v1.zip`. Upstream afirma retexture de todos os mobs, mas explicita que a versão atual contém somente mobs e uma purple gem.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da build v1 para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-eidolon-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Eidolon:Repraised 0.5.0.2, cobertura mobs + purple gem, ausência física de Edoni, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Eidolon_1.21.1_v1.zip`, versão `v1`, Release para Minecraft 1.21.1. É um compatibility pack não oficial para Eidolon:Repraised e possui escopo deliberadamente limitado.

## 1. Papel e authority
Excalibur | Eidolon Support adapta visualmente parte de **Eidolon:Repraised** ao estilo Excalibur. Eidolon:Repraised continua authority de magia, mobs, itens, blocos, rituals, recipes e qualquer state de gameplay.
O resource pack não adiciona conteúdo nem altera mecânicas.

## 2. Cobertura confirmada
A descrição oficial afirma que o pack retexturiza **todos os mobs** de Eidolon:Repraised para combinar com Excalibur.
O próprio autor delimita o restante do escopo: atualmente o pack inclui **somente os mobs e uma purple gem**. Portanto não tratar a v1 como retexture completa de blocos, itens, GUI ou toda a identidade visual do mod.

## 3. Stack físico atual
A modlist mantém Eidolon:Repraised `0.5.0.2`. O support pack instalado é `v1` para Minecraft 1.21.1.
O upstream menciona Eidolon:Edoni como integração/opção relacionada, mas o snapshot top-level auditado não identifica uma entrada separada de Eidolon:Edoni e isso não deve ser promovido a dependência instalada.

## 4. Load order
O projeto é indicado para uso junto ao Excalibur. Para seus overrides aparecerem, deve ter prioridade acima do Excalibur base e dos assets padrão de Eidolon.
Outro retexture que modifique os mesmos mobs pode substituí-lo conforme ordem dos resource packs.

## 5. Client e resource reload
Textures/models de mobs e o asset da purple gem são client-side. Resource reload pode trocar a apresentação sem alterar AI, health, drops, spells ou ritual state.

## 6. Sobreposição
Pode se sobrepor a Fresh Animations/model packs ou outros retextures de Eidolon caso toquem os mesmos assets. Não presumir compatibilidade ou incompatibilidade sem comparar caminhos/model rules concretos.
Como a cobertura é propositalmente limitada, o restante dos assets Eidolon permanecer no estilo original não é necessariamente falha.

## 7. Riscos
1. Usuário interpretar a v1 como retexture completa quando o upstream limita o conteúdo a mobs + purple gem.
2. Eidolon:Repraised 0.5.0.2 alterar entity texture/model paths.
3. Outro mob pack sobrescrever os mesmos assets.
4. Load order deixar mobs no visual original.
5. Resource reload/cache produzir variante visual stale.
6. Eidolon:Edoni ser tratado erroneamente como instalado.

## 8. Matriz de testes
- [ ] Conferir todos os mobs de Eidolon:Repraised disponíveis na build física.
- [ ] Conferir a purple gem coberta pelo pack.
- [ ] Confirmar que blocos/itens fora do escopo permanecem intencionalmente no visual de outro provider.
- [ ] Testar prioridade acima do Excalibur.
- [ ] Testar coexistência com outros packs de mobs/animação efetivamente ativos.
- [ ] Resource reload sem missing textures/models.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
- captura CurseForge do perfil: `Excalibur_Eidolon_1.21.1_v1.zip`;
- modlist física: Eidolon:Repraised `0.5.0.2`; nenhuma entrada top-level separada de Eidolon:Edoni foi identificada no snapshot auditado;
- CurseForge oficial: pack não oficial para Excalibur + Eidolon:Repraised, todos os mobs retexturizados e escopo atual limitado a mobs + purple gem.
Não foi inventariado o ZIP internamente.

> Boundary canônico: **v1 cobre mobs e uma purple gem; não deve ser documentada como overhaul completo de Eidolon**.
