# Integrated Stronghold- Alex's Mobs Integration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `intstrong_alexsmobs-1.0.0-1.21.1.zip`
- **Versão 1.21.1:** 1.0.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `intstrong_alexsmobs-1.0.0-1.21.1.zip` como fisicamente confirmado por captura do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `integrated_stronghold-1.1.4+1.21.1-neoforge.jar`, mod id `integrated_stronghold`, runtime `1.1.4+1.21.1-neoforge`, e `alexsmobs-2.1.13-neoforge+1.21.1.jar`, mod id `alexsmobs`, runtime `2.1.13`, nome de runtime Alex's Mobs Continued.
- O upstream identifica o projeto como Alex's Mobs Integration Datapack, mas não publica inventário detalhado de cada entrada do ZIP; cobertura específica permanece fail-closed.
- Integrated Stronghold continua authority da estrutura/worldgen-base; Alex's Mobs Continued continua authority das entidades/gameplay; o datapack controla apenas a integração de dados efetivamente fornecida.

## Propriedades do banco

- **Mod:** Integrated Stronghold- Alex's Mobs Integration
- **Arquivo JAR:** `intstrong_alexsmobs-1.0.0-1.21.1.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, Mobs, Worldgen
- **Função:** Datapack de integração que conecta conteúdo de Alex's Mobs ao ecossistema de dados/worldgen do Integrated Stronghold, sem assumir ownership das entidades ou da estrutura-base.
- **Dependências:** Integrated Stronghold 1.1.4+1.21.1-neoforge + Alex's Mobs Continued 2.1.13 como alvos físicos. O projeto é um datapack de integração; não substitui nenhum dos mods.
- **Sobreposição:** Pode disputar dados de integração/spawn/worldgen do stronghold com outros datapacks. Integrated Stronghold continua authority da estrutura; Alex's Mobs Continued continua authority das entidades e gameplay.
- **Compatibilidade/Riscos:** Integração data-driven entre Integrated Stronghold e Alex's Mobs. O upstream não publica inventário detalhado das entradas do ZIP; cobertura específica permanece fail-closed. Mudanças de IDs/data paths no fork Alex's Mobs Continued 2.1.13 podem quebrar a integração e devem ser smoke-tested.
- **Observações:** Arquivo físico `intstrong_alexsmobs-1.0.0-1.21.1.zip`, versão 1.0.0, release oficial para Minecraft 1.21.1. O stack físico atual usa Integrated Stronghold 1.1.4+1.21.1-neoforge e Alex's Mobs Continued 2.1.13.
- **Procedência:** CurseForge oficial Integrated Stronghold - Alex's Mobs Integration 1.0.0 + captura do perfil em 08/09/2026 + modlist(1).txt física atual de 18/09/2026 (Integrated Stronghold 1.1.4+1.21.1-neoforge; Alex's Mobs Continued 2.1.13).
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/integrated-stronghold-alexs-mobs-integration/files/7805726
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 18/09/2026 — datapack físico permanece `intstrong_alexsmobs-1.0.0-1.21.1.zip` / 1.0.0; stack físico atualizado para Integrated Stronghold 1.1.4+1.21.1-neoforge + Alex's Mobs Continued 2.1.13. Release oficial 1.0.0 para 1.21.1 permanece atual.
- **Histórico da decisão:** 2026-09-18 — target físico Alex's Mobs Continued atualizado de 2.1.11 para 2.1.13; datapack permanece 1.0.0 e nenhuma decisão curatorial nova foi tomada.

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado:** `intstrong_alexsmobs-1.0.0-1.21.1.zip`, versão `1.0.0`, release oficial para Minecraft 1.21.1.

## 1. Papel e authority
Integrated Stronghold- Alex's Mobs Integration é o datapack oficial de integração entre **Integrated Stronghold** e **Alex's Mobs**. Integrated Stronghold continua authority da estrutura e worldgen-base; Alex's Mobs Continued continua authority das entidades, AI, stats, drops e demais mecânicas.

## 2. Stack físico
O perfil contém Integrated Stronghold `1.1.4+1.21.1-neoforge` e Alex's Mobs Continued `2.1.13`. A release 1.0.0 do datapack é publicada especificamente para Minecraft 1.21.1.

## 3. Escopo fail-closed
O upstream identifica o projeto como **Alex's Mobs Integration Datapack for Integrated Stronghold**, mas não publica inventário detalhado de cada mob, tabela, spawn ou arquivo alterado. Portanto a ficha não inventa entidades específicas nem garante cobertura além da integração declarada.

## 4. Lifecycle de dados/worldgen
Como datapack associado a estrutura/worldgen, alterações devem ser validadas no mundo correto e, quando envolverem geração, em novas instâncias/novos chunks apropriados. Reload pode atualizar dados consumidos em runtime, mas não deve ser confundido com regeneração retroativa de estruturas existentes.

## 5. Sobreposição
Outros datapacks que alterem dados do Integrated Stronghold ou integrações de Alex's Mobs podem disputar os mesmos paths. A prioridade de dados define a versão efetiva.

## 6. Riscos
1. IDs de Alex's Mobs mudarem em update/fork.
2. Integrated Stronghold alterar data paths esperados.
3. Outro datapack substituir a mesma integração.
4. Teste em estrutura antiga mascarar efeito de worldgen.
5. Ausência de inventário upstream levar a inferências indevidas sobre mobs específicos.

## 7. Matriz de testes
- [ ] Confirmar datapack habilitado no mundo.
- [ ] Gerar/visitar Integrated Stronghold novo para QA quando aplicável.
- [ ] Conferir presença de integração sem IDs ausentes nos logs.
- [ ] Validar comportamento das entidades como definido pelo mod, não pelo datapack.
- [ ] Testar reload/restart sem erro de data pack.
- [ ] Confirmar que remover o pack não remove Alex's Mobs ou Integrated Stronghold.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma filename, versão 1.0.0, Minecraft 1.21.1 e natureza de Alex's Mobs Integration Datapack. Detalhes internos não publicados permanecem fail-closed.

> Boundary canônico: **Integrated Stronghold controla a estrutura; Alex's Mobs Continued controla as entidades; este datapack controla apenas a camada de integração de dados que fornece**.
