# Mobs Refreshed + Fresh Animations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81f99ccdc3965fac6252
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `mobs-refreshed-fa-v2.2.zip`
- **Versão 1.21.1:** 2.2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `mobs-refreshed-fa-v2.2.zip`.
- A página oficial do arquivo inclui Minecraft 1.21.1 e registra explicitamente compatibilidade com Mobs Refreshed v2.2.
- O stack registrado no dossiê usa Mobs Refreshed 2.2, Fresh Animations 1.10.4, EMF 3.3.5 e ETF 7.2.1.

## Propriedades do banco

- **Mod:** Mobs Refreshed + Fresh Animations
- **Arquivo JAR:** `mobs-refreshed-fa-v2.2.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, Mobs
- **Função:** Resource pack de compatibilidade que concilia os modelos/visuais do Mobs Refreshed com as animações do Fresh Animations para os mobs cobertos.
- **Dependências:** Mobs Refreshed 2.2 + Fresh Animations 1.10.4 como bases visuais; EMF 3.3.5 e ETF 7.2.1 compõem a infraestrutura do stack de entidades.
- **Sobreposição:** É uma camada de bridge intencional entre duas bases visuais e deve ter prioridade suficiente para que os assets de compat prevaleçam. Pode ainda disputar entidades com outros packs CEM/Refreshed específicos.
- **Compatibilidade/Riscos:** O arquivo v2.2 suporta explicitamente 1.21.1 e foi feito para compatibilidade com Mobs Refreshed v2.2. Deve prevalecer sobre as duas bases nos assets de ponte; outros CEM/entity packs ainda podem competir nos mesmos mobs.
- **Observações:** Arquivo físico `mobs-refreshed-fa-v2.2.zip`, versão 2.2. O changelog oficial diz explicitamente que esta build torna o addon compatível com Mobs Refreshed v2.2; 1.21.1 consta entre as versões suportadas.
- **Procedência:** CurseForge oficial Mobs Refreshed + Fresh Animations v2.2 + captura Resource Packs do perfil em 08/09/2026 + páginas já catalogadas Mobs Refreshed 2.2/Fresh Animations 1.10.4.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/mobs-refreshed-fresh-animations/files/6723922
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Mobs Refreshed + Fresh Animations 2.2, suporte 1.21.1, Mobs Refreshed 2.2 + FA 1.10.4, bridge/load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** —

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `mobs-refreshed-fa-v2.2.zip`, versão `2.2`. A página oficial do arquivo inclui Minecraft `1.21.1` e informa compatibilidade com Mobs Refreshed v2.2.

## 1. Papel e authority
Mobs Refreshed + Fresh Animations é a camada de **compatibilidade visual** entre Mobs Refreshed e Fresh Animations. Minecraft continua authority de entidades, AI, spawn, stats e drops; as duas bases e este bridge controlam apenas modelos/texturas/animações.

## 2. Targets físicos
O perfil contém **Mobs Refreshed 2.2** e **Fresh Animations 1.10.4**, exatamente as bases visuais que este addon precisa conciliar. EMF `3.3.5` e ETF `7.2.1` fornecem a infraestrutura CEM/texture do stack.

## 3. Release 2.2
O changelog oficial da build `mobs-refreshed-fa-v2.2.zip` declara que o addon foi tornado compatível com **Mobs Refreshed v2.2**. A mesma build lista Minecraft 1.21.1 entre as versões suportadas.

## 4. Load order
Este pack existe para fornecer os assets de ponte entre as duas bases. Portanto seus overrides de compatibilidade precisam prevalecer sobre os assets conflitantes de Mobs Refreshed e Fresh Animations conforme a ordem de resource packs do perfil.

## 5. Boundary de cobertura
O addon não deve ser interpretado como nova coleção de mobs nem como expansão de gameplay. Ele só adapta os mobs já cobertos por Mobs Refreshed para coexistirem visualmente com Fresh Animations.

## 6. Sobreposição e riscos
1. Bridge ficar abaixo de uma das bases e perder overrides necessários.
2. Outro CEM/resource pack substituir o mesmo mob.
3. EMF/ETF cache manter modelo anterior após reload.
4. Atualização de uma base ocorrer sem atualização correspondente do bridge.
5. Mistura parcial de models/textures por prioridade inconsistente.

## 7. Matriz de testes
- [ ] Confirmar Mobs Refreshed 2.2 e Fresh Animations 1.10.4 ativos.
- [ ] Carregar bridge com prioridade adequada sobre as bases.
- [ ] Amostrar diferentes mobs cobertos pelo Mobs Refreshed.
- [ ] Verificar animações em idle/movement/combat quando aplicável.
- [ ] Resource reload/relog sem entidade invisível/deformada.
- [ ] Comparar com outros entity packs específicos ativos.
- [ ] Confirmar que pack on/off não altera AI, spawn, stats ou drops.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma filename, versão 2.2, suporte a 1.21.1 e compatibilidade explícita com Mobs Refreshed v2.2. O catálogo limita o escopo à ponte visual publicada.

> Boundary canônico: **Mobs Refreshed define o redesign visual, Fresh Animations fornece animações, este addon concilia ambos; Minecraft mantém toda authority de gameplay**.
