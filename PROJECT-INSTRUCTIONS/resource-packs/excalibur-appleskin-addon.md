# Excalibur x AppleSkin addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81319315f81dbf581c6d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur AppleSkin Addon.zip`
- **Versão 1.21.1:** 0.0.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur AppleSkin Addon.zip` como instalado e cita uma captura da pasta Resource Packs do perfil em 08/09/2026 como evidência física.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença do resource pack nesta execução é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em prova de resource pack.
- A modlist física acessível de 08/09/2026 confirma o provider-alvo `appleskin-neoforge-mc1.21-3.0.9.jar`, runtime `3.0.9+mc1.21`.

## Propriedades do banco

- **Mod:** Excalibur x AppleSkin addon
- **Arquivo JAR:** `Excalibur AppleSkin Addon.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 0.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, QoL
- **Função:** Addon visual do Excalibur para AppleSkin que estiliza os overlays de hunger/saturation sem alterar cálculo de fome, saturação ou previsão de comida.
- **Dependências:** Excalibur base + AppleSkin 3.0.9+mc1.21. O projeto foi criado primariamente para 1.20.1 e lista 1.21.1 como suportado, mas o autor alerta compatibilidade não garantida em versões posteriores.
- **Sobreposição:** Pode disputar hunger/saturation HUD assets com outros GUI/HUD packs. AppleSkin continua authority dos valores/overlays lógicos; esta camada apenas altera aparência.
- **Compatibilidade/Riscos:** GATE DE QA: v0.0.1 foi feita primariamente para 1.20.1; autor diz que pode ou não funcionar em versões mais novas. Stack físico usa AppleSkin 3.0.9+mc1.21. Riscos de HUD sprite drift e overlap com GUI/HUD packs.
- **Observações:** Arquivo instalado `Excalibur AppleSkin Addon.zip` coincide com a única release oficial v0.0.1. A página lista 1.21.1, mas mantém disclaimer explícito de origem 1.20.1/use at own risk.
- **Procedência:** CurseForge oficial Excalibur x AppleSkin addon v0.0.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física AppleSkin 3.0.9+mc1.21.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-x-appleskin-addon
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v0.0.1, AppleSkin 3.0.9+mc1.21, disclaimer 1.20.1→1.21.1, HUD authority, overlap, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur AppleSkin Addon.zip`. A única release oficial correspondente é `v0.0.1`; o alvo físico atual é AppleSkin `3.0.9+mc1.21`.

## 1. Papel e authority
Excalibur x AppleSkin addon adapta a apresentação dos overlays do AppleSkin ao estilo Excalibur. AppleSkin continua authority da leitura/apresentação lógica de hunger, saturation e food values; Minecraft continua authority das mecânicas reais de alimentação.

## 2. Cobertura confirmada
O projeto declara suporte visual ao AppleSkin e exibe o overlay customizado. A ficha não presume alteração de todos os elementos de HUD além dos assets efetivamente fornecidos.

## 3. Boundary de versão
A v0.0.1 foi feita **primariamente para Minecraft 1.20.1**. O próprio autor informa que pode ou não continuar funcionando em versões mais novas/antigas. Embora 1.21.1 apareça entre as versões suportadas, essa ressalva mantém a compatibilidade real **dependente de QA**.

## 4. Stack físico
AppleSkin físico: `3.0.9+mc1.21`. Mudanças de sprite/layout no AppleSkin ou no HUD de Minecraft 1.21.1 podem produzir desvio visual mesmo com o ZIP carregando normalmente.

## 5. Load order e HUD overlap
Outros packs de GUI/HUD podem sobrescrever hunger/saturation assets. A prioridade precisa ser testada junto ao stack Mandala/Excalibur ativo.

## 6. Client e reload
É conteúdo client-side. Resource reload deve apenas trocar sprites/textures; hunger, saturation, exhaustion e food restoration não podem mudar.

## 7. Riscos
1. Pack originalmente desenhado para 1.20.1.
2. AppleSkin 3.0.9 usar asset path/layout diferente.
3. HUD/GUI pack concorrente sobrescrever o addon.
4. Ícones ficarem desalinhados em GUI scales diferentes.
5. Resource reload manter sprite stale.

## 8. Matriz de testes
- [ ] Hunger bar em estado normal.
- [ ] Saturation overlay do AppleSkin.
- [ ] Food preview segurando/comendo itens.
- [ ] Diferentes GUI scales.
- [ ] Convivência com outros GUI/HUD packs ativos.
- [ ] Resource reload sem missing sprite.
- [ ] Confirmar que pack on/off não altera valores de hunger/saturation.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma v0.0.1, o filename correspondente, objetivo de suporte ao AppleSkin e disclaimer explícito de origem 1.20.1. A compatibilidade 1.21.1 não é tratada como garantida sem QA.

> Boundary canônico: **o addon é visual; o disclaimer upstream 1.20.1→1.21.1 permanece um gate de QA e não deve ser apagado pela simples presença no menu**.
