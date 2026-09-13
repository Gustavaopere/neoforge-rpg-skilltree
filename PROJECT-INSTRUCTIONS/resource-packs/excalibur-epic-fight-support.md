# Excalibur | Epic Fight Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81abbe01e5df1b263371
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_EpicFight_0.2_1.21.1.zip`
- **Versão 1.21.1:** 0.2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_EpicFight_0.2_1.21.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`, mod id `epicfight`, runtime `21.17.3.1`.
- A build visual 0.2 é anterior ao Epic Fight físico atual; assets posteriores permanecem superfície de regressão e não são considerados cobertos por inferência.
- O conflito Epic Fight × Excalibur Fresh Animations Patch é documentado pelo upstream do patch e permanece aberto para QA/decisão curatorial; não é resolvido automaticamente por load order.

## Propriedades do banco

- **Mod:** Excalibur | Epic Fight Support
- **Arquivo JAR:** `Excalibur_EpicFight_0.2_1.21.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, RPG
- **Função:** Compatibility resource pack 32x que adapta textures do Epic Fight ao estilo Excalibur; não altera animações, movesets, stamina, skills ou combat state.
- **Dependências:** Uso visual pretendido: Excalibur + Epic Fight. Stack físico atual: Epic Fight 21.17.3.1. O próprio Fresh Animations Patch instalado declara Epic Fight incompatível, exigindo QA/decisão de prioridade.
- **Sobreposição:** Sobreposição direta de superfície visual/animação com Excalibur Fresh Animations Patch/Fresh Animations; upstream do patch marca Epic Fight incompatível. Não remover automaticamente sem QA e decisão curatorial.
- **Compatibilidade/Riscos:** CONFLITO DOCUMENTADO: Excalibur Fresh Animations Patch lista Epic Fight como incompatível. Riscos adicionais: pack 0.2 de 2025 contra Epic Fight 21.17.3.1 atual, assets novos sem cobertura, model/animation override e load order.
- **Observações:** Arquivo instalado `Excalibur_EpicFight_0.2_1.21.1.zip`, release 0.2 de 22/06/2025. Gallery oficial confirma pelo menos cobertura visual de swords; manifesto completo não foi publicado.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Epic Fight 0.2 + documentação oficial do Fresh Animations Patch.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-epic-fight-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Epic Fight 21.17.3.1, escopo 0.2, load order, conflito oficial com Fresh Animations Patch, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_EpicFight_0.2_1.21.1.zip`, versão `0.2`, Release para Minecraft 1.21.1. O alvo físico é Epic Fight `21.17.3.1`.

## 1. Papel e authority
O pack adapta textures/assets do Epic Fight ao estilo Excalibur. Epic Fight continua authority de combat mode, animations/movesets, stamina, skills, hit detection, damage e networking.

## 2. Cobertura confirmada e limite
A publicação confirma o objetivo de compatibilidade visual; a gallery oficial mostra cobertura de swords. Sem manifesto integral do ZIP, não afirmar cobertura total de weapons, armor, GUI ou entidades.

## 3. Drift com Epic Fight atual
A build visual 0.2 é de 22/06/2025, enquanto o pack usa Epic Fight 21.17.3.1. Assets adicionados ou renomeados posteriormente podem permanecer no visual padrão ou não casar com modelos atuais.

## 4. Conflito oficial com Fresh Animations Patch
O **Excalibur | Fresh Animations Patch** instalado declara **Epic Fight** incompatível. Isso é uma incompatibilidade publicada pelo upstream do patch, não uma inferência desta auditoria.
Não remover automaticamente nenhum dos dois: o catálogo registra o conflito e exige QA/decisão curatorial sobre qual stack visual/animação prevalece.

## 5. Load order e reload
O support pack deve ficar acima do Excalibur base para seus assets prevalecerem. Porém, quando combinado com Fresh Animations Patch, simples prioridade pode não resolver incompatibilidades de model/animation definitions.
Resource reload deve afetar apenas apresentação; combat state continua server-authoritative.

## 6. Riscos
1. Conflito Epic Fight × Fresh Animations Patch.
2. Asset antigo não acompanhar Epic Fight 21.17.3.1.
3. Model/texture override quebrar animação ou produzir visual desalinhado.
4. Outro weapon retexture vencer o mesmo path.
5. Resource reload deixar model cache inconsistente.

## 7. Matriz de testes
- [ ] Testar weapons cobertas pelo pack.
- [ ] Entrar/sair do combat mode sem modelo quebrado.
- [ ] Testar animações básicas com Fresh Animations Patch ativo e depois isolado.
- [ ] Verificar armor/GUI apenas onde houver asset confirmado.
- [ ] Resource reload sem missing model/texture.
- [ ] Registrar qual combinação visual é escolhida se a incompatibilidade se manifestar.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge confirma a build 0.2 e o objetivo de integrar Epic Fight ao Excalibur. A página oficial do Fresh Animations Patch lista Epic Fight entre incompatibilidades. O ZIP não foi inventariado internamente.

> Boundary canônico: **o support pack é visual; o conflito Epic Fight × Fresh Animations Patch é real e deve permanecer aberto até QA/decisão, sem alteração automática do pack**.
