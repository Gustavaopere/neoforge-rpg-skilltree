# Excalibur | Transmog support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db8189bc20fa0362e71bac
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Transmog_v1.0.zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Transmog_v1.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma `transmog-neoforge-1.6.0+1.21.1.jar`, mod id `transmog`, runtime `1.6.0`.

## Propriedades do banco

- **Mod:** Excalibur | Transmog support
- **Arquivo JAR:** `Excalibur_Transmog_v1.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, RPG
- **Função:** Compatibility resource pack que redesenha os assets do mod Transmog para o estilo Excalibur, mantendo intacta a mecânica de transmog/cosmetic appearance.
- **Dependências:** Excalibur base + Transmog 1.6.0 para NeoForge 1.21.1. Conteúdo client-side; Transmog continua authority do estado de aparência e preservação de comportamento/stats do item.
- **Sobreposição:** Pode disputar GUI/icons/assets de Transmog com outros packs. Excalibur base deve ficar abaixo; o mod continua owner do cosmetic state e da preservação de stats/comportamento.
- **Compatibilidade/Riscos:** Riscos de asset/UI drift entre Transmog 1.6.0 e v1.0 do resource pack, conflito com outros GUI/item retextures e load order. Não altera item stats, NBT ou regras de transmog.
- **Observações:** Arquivo instalado `Excalibur_Transmog_v1.0.zip`; upstream lista suporte explícito a Minecraft 1.21.1. Alvo físico atual: Transmog 1.6.0.
- **Procedência:** CurseForge oficial Excalibur | Transmog support v1.0 + captura Resource Packs do perfil em 08/09/2026 + modlist física Transmog 1.6.0+1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-transmog-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v1.0, Transmog 1.6.0, appearance-state boundary, load order, overlap, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Transmog_v1.0.zip`, versão `1.0`, com suporte oficial a Minecraft 1.21.1. O alvo físico atual é Transmog `1.6.0`.

## 1. Papel e authority
Excalibur | Transmog support redesenha assets do mod Transmog para a estética Excalibur. **Transmog** continua authority do estado cosmético/aparência aplicado ao item e das regras que preservam seu comportamento real.

## 2. Cobertura confirmada
O upstream descreve o pack como um redraw do Transmog em estilo Excalibur. Sem inventário do ZIP, não se presume cobertura integral de toda tela/item além dos assets fornecidos.

## 3. Stack físico
O mod físico é `transmog-neoforge-1.6.0+1.21.1.jar`, runtime `1.6.0`. A v1.0 do resource pack lista explicitamente 1.21.1 entre as versões suportadas.

## 4. Authority de aparência versus stats
Transmog permite mudar a aparência sem alterar o comportamento efetivo do item. O resource pack apenas muda a apresentação dos próprios assets do Transmog; não deve ser usado como fonte de verdade para stats, NBT, enchantments ou cosmetic state.

## 5. Load order e reload
Para manter a estética Excalibur, o support pack deve prevalecer sobre a base nos assets específicos. Outros GUI/item packs podem vencer paths individuais. Resource reload não pode alterar o estado de transmog salvo.

## 6. Riscos
1. Transmog 1.6.0 possuir asset/UI posterior ao pack v1.0.
2. Outro GUI pack sobrescrever sprites/icons.
3. Item preview ficar inconsistente com cosmetic state.
4. Resource reload manter asset stale.
5. Usuário confundir retexture do UI com alteração de item stats.

## 7. Matriz de testes
- [ ] Abrir superfícies/UI do Transmog disponíveis.
- [ ] Aplicar/remover aparência cosmética e conferir visual.
- [ ] Confirmar que stats/behavior do item permanecem iguais.
- [ ] Testar prioridade junto a outros GUI packs.
- [ ] Resource reload sem missing texture/model.
- [ ] Relog preservando cosmetic state independentemente do resource pack.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.0 e suporte a 1.21.1; a modlist física confirma Transmog 1.6.0. O escopo do resource pack permanece estritamente visual.

> Boundary canônico: **Transmog controla cosmetic state e preservação funcional do item; o support pack controla somente sua apresentação visual**.
