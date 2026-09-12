# Boss Refreshed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db819a94a7f85ef57e55df
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `boss-refreshed-v2-1.19-1.21.zip`
- **Versão 1.21.1:** v2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `boss-refreshed-v2-1.19-1.21.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, runtime `3.3.5`, infraestrutura CEM suportada pelo autor.

## Propriedades do banco

- **Mod:** Boss Refreshed
- **Arquivo JAR:** `boss-refreshed-v2-1.19-1.21.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** v2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Mobs
- **Função:** Overhaul visual de bosses vanilla com modelos mais detalhados e aparência mais agressiva/épica, sem alterar AI, health, damage, phases, drops ou progressão.
- **Dependências:** Entity Model Features é a infraestrutura recomendada/suportada pelo autor; stack físico contém EMF 3.3.5. Minecraft continua authority dos bosses e de todo gameplay.
- **Sobreposição:** Compete com outros resource packs/CEM que alterem Wither, Warden, Ender Dragon ou Elder Guardian. Mobs Refreshed separa editorialmente bosses; Enhanced Boss Bars altera barra/UI, não o modelo da entidade.
- **Compatibilidade/Riscos:** Usa custom entity models e foi feito com Entity Model Features; EMF 3.3.5 está presente. Pode colidir com outros boss/entity model packs. Enhanced Boss Bars atua em UI e não é automaticamente conflito de model; overlap deve ser avaliado por asset.
- **Observações:** Arquivo físico `boss-refreshed-v2-1.19-1.21.zip`, versão v2. A release oficial `Boss Refreshed v2 1.19-1.21.1` suporta Minecraft 1.21.1. Galeria oficial confirma Wither, Warden, Ender Dragon e Elder Guardian.
- **Procedência:** CurseForge oficial Boss Refreshed v2 para 1.21.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física EMF 3.3.5.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/boss-refreshed
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Boss Refreshed v2, 1.21.1, Wither/Warden/Ender Dragon/Elder Guardian, EMF, overlap, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `boss-refreshed-v2-1.19-1.21.zip`, versão `v2`. A release oficial `Boss Refreshed v2 1.19-1.21.1` suporta Minecraft 1.21.1.

## 1. Papel e authority
Boss Refreshed é um overhaul **visual** de bosses vanilla, com modelos mais detalhados, agressivos e estilizados. Minecraft continua authority de AI, health, damage, phases, boss events, drops e progressão.

## 2. Cobertura confirmada
A galeria oficial confirma cobertura de **Wither, Warden, Ender Dragon e Elder Guardian**. O catálogo não extrapola essa evidência para outros bosses sem confirmação adicional.

## 3. Infraestrutura CEM
O autor declara que o pack foi feito com **Entity Model Features (EMF)** e que OptiFine deixou de ser a opção suportada. O stack físico contém EMF `3.3.5`.

## 4. Boundary com outros packs
Mobs Refreshed trata bosses como família separada e não deve ser usado como evidência de cobertura aqui. Outros CEM/resource packs dos mesmos bosses podem competir diretamente. Enhanced Boss Bars atua na barra/UI e não é, por si só, substituto de entity model.

## 5. Load order e reload
Se outro boss model pack tocar os mesmos paths, maior prioridade vence. Resource reload/relog deve reconstruir models/textures sem alterar estado da entidade ou progresso da luta.

## 6. Riscos
1. Outro CEM sobrescrever boss models/textures.
2. EMF cache/model rule ficar stale após reload.
3. Atualização do jogo alterar model parts/path esperado.
4. Mistura parcial de textures e models entre packs.
5. Confundir overhaul visual com mudança de dificuldade.

## 7. Matriz de testes
- [ ] Wither em combate.
- [ ] Warden em estados representativos.
- [ ] Ender Dragon durante voo/pouso.
- [ ] Elder Guardian.
- [ ] Confirmar EMF 3.3.5 ativo.
- [ ] Resource reload/relog sem entidade invisível/deformada.
- [ ] Confirmar que pack on/off não altera AI, stats, phases ou drops.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v2 para 1.21.1, uso de EMF e os quatro bosses exibidos na galeria. O escopo permanece estritamente visual.

> Boundary canônico: **Boss Refreshed controla apresentação; Minecraft controla integralmente os bosses e seu gameplay**.
