# Wayward Attributes

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8125b69dc8e66b3878ae
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Wayward Attributes
- **Arquivo JAR:** `wayward_attributes-1.21.1-1.1.1.jar`
- **Versão 1.21.1:** `1.1.1`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; boundaries UI↔attribute provider e riscos de tooltip catalogados.
- **Categoria:** QoL; Visual; RPG
- **Compatibilidade/Riscos:** Risco principal é composição de tooltip/UI com Tooltip Overhaul e outros formatadores: linhas duplicadas, clipping e apresentação repetida. Não usar tooltip como state autoritativo nem confundir propriedades expostas com novos modifiers persistentes.
- **Decisão:** Sem decisão
- **Dependências:** NeoForge 1.21.1. Atua sobre atributos vanilla/modded já registrados pelos respectivos providers.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/wayward-attributes
- **Função:** Overhaul principalmente visual/data-driven de atributos em tooltips; adiciona ícones e expõe sweeping de swords e propriedades de bows/crossbows como atributos visíveis.
- **Histórico da decisão:** vazio
- **Observações:** Mod id `wayward_attributes`, runtime 1.1.1. É camada de apresentação/normalização visual; Apothic/Pufferfish/vanilla continuam authorities de seus próprios atributos.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Wayward Attributes 1.1.1 + Guia Gameplay atual.
- **Sobreposição:** Sobreposição visual com outros formatadores de tooltip; não é substituto de frameworks de atributos ou progressão.
- **Data da última decisão:** 2026-09-09

> 📊 **ESCOPO CANÔNICO.** Runtime físico: `wayward_attributes-1.21.1-1.1.1.jar`, mod id `wayward_attributes`, versão `1.1.1`. Wayward Attributes é principalmente uma camada **visual/data-driven de apresentação de atributos**, não um segundo framework completo de progressão RPG.

## 1. Função confirmada
A documentação oficial descreve:
- overhaul visual de como atributos aparecem em tooltips;
- ícones associados a atributos por sistema **data-driven**;
- propriedades de sweeping damage de swords expostas como atributos;
- propriedades de bows e crossbows expostas no mesmo modelo de atributos.
A release 1.1.1 é a build NeoForge 1.21.1 atual do projeto.

## 2. Authority e ownership
Wayward Attributes controla a **apresentação** e a exposição visual de determinadas propriedades. A grandeza mecânica real continua pertencendo ao atributo/provider original.
Exemplos:
- Apothic Attributes continua authority de crit, dodge, life steal, pierce/shred etc.;
- atributos vanilla/modded continuam sendo calculados pelos respectivos providers;
- Wayward não deve ser usado como fonte alternativa de modifier gameplay apenas porque exibe o valor em tooltip.

## 3. Sistema data-driven
Ícones de atributos são definidos por dados, o que permite ampliar a apresentação sem hardcode centralizado.
Para resource packs/mods próprios:
- preferir IDs/tags/formatos documentados pelo provider;
- não criar ícone duplicado por mixin se o sistema data-driven resolver;
- resource reload deve atualizar a apresentação sem alterar o atributo real.

## 4. Armas vanilla
Swords passam a expor propriedades de sweeping no formato de atributos. Bows/crossbows também apresentam propriedades relevantes como atributos visíveis.
Isso melhora comparabilidade de equipamentos, mas não autoriza reinterpretar essas propriedades como novos stats permanentes do jogador.

## 5. Compatibilidade com tooltips do pack
O principal overlap é visual com **Tooltip Overhaul** e outros formatadores/frames/tooltips.
Riscos típicos:
- linhas duplicadas;
- ordem/indentação quebrada;
- ícones sobrepostos;
- clipping em resoluções/GUI scales diferentes;
- informação repetida por dois mods que descrevem o mesmo attribute modifier.

## 6. Client / server
CurseForge classifica a build como Client & Server. A apresentação é client-facing, enquanto os atributos mecânicos exibidos continuam server-authoritative quando afetam gameplay.
Nenhum mod próprio deve aceitar valor de tooltip enviado/formatado pelo cliente como mutation ou prova de stat no servidor.

## 7. Boundary para RPG Skill Tree
- Ler o atributo real/provider, não o texto da tooltip.
- Não conceder Mastery por abrir/inspecionar equipamento repetidamente.
- Não duplicar modifiers só para fazê-los aparecer no painel.
- Se uma propriedade só estiver exposta visualmente e não houver hook mecânico estável, uma perk provider-specific deve ficar **fail-closed**.

## 8. Riscos
1. **Tooltip collision:** múltiplos formatadores atuando na mesma linha.
2. **Duplicate presentation:** atributo já mostrado por outro mod aparece duas vezes.
3. **Client inference:** tooltip usada incorretamente como state autoritativo.
4. **Data reload:** ícones/config de apresentação ficam stale.
5. **Weapon property interpretation:** propriedades expostas visualmente confundidas com novos modifiers persistentes.

## 9. Matriz de testes
- [ ] Cliente e dedicated server iniciam com Wayward 1.1.1.
- [ ] Atributos vanilla/modded exibem ícones sem linhas duplicadas.
- [ ] Sword sweeping aparece de forma coerente.
- [ ] Bow/crossbow exibem propriedades sem alterar comportamento.
- [ ] Tooltip Overhaul e demais formatadores não causam clipping/repetição.
- [ ] GUI scales diferentes mantêm legibilidade.
- [ ] Resource reload atualiza ícones/format sem alterar stats.
- [ ] Valor exibido corresponde ao attribute state real do servidor.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências
- **Modlist física 08/09/2026:** `wayward_attributes-1.21.1-1.1.1.jar`, runtime 1.1.1.
- CurseForge oficial: “primarily visual overhaul”, sistema data-driven de ícones, sweeping em swords e propriedades de bows/crossbows expostas como attributes.
- Guia Gameplay do projeto: distinção entre Wayward e providers de atributos como Apothic/Pufferfish.

## 11. Limitação
Não foram inspecionados nesta etapa os arquivos data-driven exatos nem event hooks/render pipeline da 1.1.1. Integração de UI custom deve validar esses recursos antes de manipular internals.
