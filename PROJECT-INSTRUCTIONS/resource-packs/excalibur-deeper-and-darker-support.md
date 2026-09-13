# Excalibur | Deeper and Darker Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db815592f6d8f298bd8671
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Deeper and Darker Support.zip`
- **Versão própria:** não publicada; não inventar versão semântica
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Deeper and Darker Support.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Deeper and Darker `1.4.1`. O support foi publicado em 01/03/2025 e não possui manifesto público de cobertura; completude permanece fail-closed.

## Propriedades do banco

- **Mod:** Excalibur | Deeper and Darker Support
- **Arquivo JAR:** `Excalibur Deeper and Darker Support.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Compatibility resource pack que adapta visualmente Deeper and Darker ao estilo Excalibur; cobertura exata não é publicada pelo upstream.
- **Dependências:** Uso visual pretendido: Excalibur + Deeper and Darker. Stack físico atual: Deeper and Darker 1.4.1. Não é dependência de gameplay/servidor.
- **Sobreposição:** Sobrepõe somente assets Deeper and Darker presentes no ZIP. Outros retextures do mesmo namespace podem prevalecer conforme prioridade; não há sobreposição de gameplay.
- **Compatibilidade/Riscos:** Pack visual publicado em 01/03/2025 contra Deeper and Darker 1.4.1 atual; risco de assets posteriores sem cobertura, rename de paths, fallback visual e colisão com outros retextures. Completude permanece não confirmada.
- **Observações:** Arquivo instalado `Excalibur Deeper and Darker Support.zip`. A página oficial não fornece versão semântica nem inventário completo de assets; o campo de versão permanece sem valor inventado.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da release para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-deeper-and-darker-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; alvo Deeper and Darker 1.4.1, cobertura fail-closed, drift temporal, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Deeper and Darker Support.zip`, Release publicada para Minecraft 1.21.1. A página oficial confirma o objetivo de oferecer suporte visual Excalibur ao mod Deeper and Darker, mas não publica manifesto completo de cobertura.

## 1. Papel e authority
Excalibur | Deeper and Darker Support é um compatibility resource pack. **Deeper and Darker** continua authority de dimensão, mobs, blocos, itens, worldgen, loot e qualquer comportamento. O support pack altera somente recursos visuais que existirem no ZIP.

## 2. Cobertura conhecida e limite
O upstream descreve o projeto de forma ampla como suporte Excalibur para Deeper and Darker, mas não enumera publicamente todos os blocos, itens, mobs, GUIs ou models incluídos nesta build.
Por isso a ficha não transforma o nome do projeto em claim de cobertura total. Qualquer asset não auditado internamente permanece `não confirmado`.

## 3. Stack físico atual
A modlist mantém Deeper and Darker `1.4.1` para NeoForge 1.21.1. O support pack oficial disponível para 1.21.1 foi publicado em **01/03/2025**.
Essa distância temporal cria uma superfície de drift: conteúdo ou assets adicionados/renomeados pelo mod depois da publicação do resource pack podem permanecer no estilo original ou gerar fallback.

## 4. Load order
Para que os assets de compatibilidade prevaleçam sobre o Excalibur base e os assets padrão do mod, o support pack deve ter prioridade visual adequada, operacionalmente acima do Excalibur.
Outro resource pack de Deeper and Darker com prioridade superior pode substituir seus overrides.

## 5. Client e resource reload
O pack é visual/client-side. Ativar, remover ou reordenar deve afetar textures/models/assets após resource reload sem modificar dimensão, bloco state, mob state, inventários ou save.

## 6. Sobreposição
Pode sobrepor qualquer outro retexture para Deeper and Darker. A comparação deve ser por asset path e resultado visual concreto.
Não atribuir ao support pack alterações de Fresh Animations, mob models ou shaders que venham de outro resource pack/mod sem evidência específica.

## 7. Riscos
1. Deeper and Darker 1.4.1 possuir assets posteriores ao support pack de 2025.
2. Cobertura parcial não documentada produzir mistura de estilos.
3. Rename de texture/model path causar fallback ou missing texture.
4. Load order incorreto impedir overrides.
5. Outro pack sobrescrever os mesmos assets.
6. Resource reload manter cache visual stale até refresh/relog.

## 8. Matriz de testes
- [ ] Conferir blocos/itens principais de Deeper and Darker 1.4.1.
- [ ] Conferir mobs/entidades visualmente relevantes sem assumir cobertura prévia.
- [ ] Conferir conteúdos mais recentes do mod para detectar assets sem suporte.
- [ ] Testar prioridade acima/abaixo de Excalibur.
- [ ] Resource reload sem missing textures/models.
- [ ] Verificar conflitos com outros packs que alterem o mesmo namespace.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
- captura CurseForge do perfil: `Excalibur Deeper and Darker Support.zip` instalado;
- modlist física: Deeper and Darker `1.4.1`;
- CurseForge oficial: release de 01/03/2025 para Minecraft 1.21.1, 16x/medieval/mod support.
Sem inventário público de assets ou auditoria interna do ZIP, completude permanece fail-closed.

> Boundary canônico: **Deeper and Darker controla gameplay; este pack controla apenas os assets visuais que realmente contém**.
