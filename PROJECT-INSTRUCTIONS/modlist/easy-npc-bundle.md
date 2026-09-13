# Easy NPC: Bundle

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db816abbfef142ff24afa7  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Easy NPC: Bundle
- **Arquivo JAR:** `easy_npc_bundle-neoforge-1.21.1-7.11.0.jar`
- **Versão 1.21.1:** `7.11.0`
- **Categoria:** RPG; QoL
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all
- **Função:** Meta-package/bundle da família Easy NPC que declara/organiza a instalação alinhada do Core e Config UI; não implementa um segundo sistema de NPC nem substitui os módulos funcionais.
- **Dependências:** Easy NPC Core + Easy NPC Config UI; NeoForge 1.21.1. No pack atual, Core, Bundle e Config UI existem como três JARs top-level separados e alinhados pelo filename/release 7.11.0.
- **Compatibilidade/Riscos:** Risco principal é misturar versões diferentes da família ou interpretar Bundle como provider de gameplay. A arquitetura moderna evita jar-in-jar justamente para não produzir mod IDs duplicados, classloading ambíguo e dependências que enxergam apenas o Core.
- **Sobreposição:** Não é duplicata funcional do Easy NPC Core. A sobreposição de nome é de distribuição: Bundle agrega dependências, Core fornece NPCs, Config UI fornece a interface.
- **Observações:** O Bundle é conveniência de distribuição/coordenação. Os três JARs estão presentes fisicamente; gameplay e NPC state pertencem ao Core, enquanto Config UI fornece configuração/networking. Não catalogar conteúdo do Core como se estivesse registrado pelo Bundle.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `easy_npc_bundle-neoforge-1.21.1-7.11.0.jar`, mod id `easy_npc_bundle` e versão 7.11.0. Documentação oficial do Bundle confirma que ele não contém os módulos e usa dependências para instalar Core + Config UI.
- **Histórico da decisão:** sem histórico adicional registrado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — bundle 7.11.0, função de distribuição/dependency aggregation, relação Core+Config UI, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-09-06.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `easy_npc_bundle-neoforge-1.21.1-7.11.0.jar` · mod id `easy_npc_bundle` · versão `7.11.0` · NeoForge 1.21.1.

## 1. Papel no modpack
Easy NPC: Bundle é um **meta-package de distribuição** da família Easy NPC. Sua função é facilitar instalação e alinhamento de dependências, não criar NPCs, diálogos, trades ou state próprio equivalente ao Core.

## 2. Arquitetura moderna do bundle
A documentação oficial do projeto esclarece que o Bundle moderno **não contém os módulos Easy NPC dentro do próprio JAR**. Em vez disso, ele declara dependências para instalar os módulos necessários separadamente.

Essa arquitetura evita os problemas do antigo modelo jar-in-jar: mod IDs duplicados, classloading ambíguo e mods externos detectando apenas parte da família.

## 3. Dependências funcionais
O Bundle organiza:
- **Easy NPC Core** — provider real de NPC state/gameplay;
- **Easy NPC Config UI** — ferramentas gráficas e networking de configuração.

No pack atual os três aparecem como JARs top-level separados, todos identificados pelo filename/publicação na linha 7.11.0.

## 4. Authority / ownership
- **Bundle:** relação de dependência/distribuição.
- **Core:** NPCs, persistência, dialogs, actions, trades e behavior.
- **Config UI:** screens/config flow/networking correspondente.

Nenhuma integração deve consultar o Bundle para obter NPC state quando o contrato pertence ao Core.

## 5. Client / Server
O Bundle pode estar presente nos dois lados por causa de dependency resolution do modpack, mas não deve ser tratado como gameplay authority. Client/server contracts reais vêm dos módulos que ele exige.

## 6. Lifecycle
O ponto crítico é o **bootstrap/dependency graph**:
- todas as dependências exigidas devem estar presentes;
- versões da família devem permanecer compatíveis;
- não carregar duas cópias do mesmo módulo;
- atualização do Bundle deve ser acompanhada de verificação do Core/Config UI efetivamente resolvidos.

## 7. Atualização e version alignment
Misturar Bundle 7.11.0 com módulos de outra linha pode criar API/GUI/network drift mesmo que o launcher permita boot. Para o pack atual, os filenames Core/Config UI também apontam para 7.11.0.

A coluna física de versão do Core está vazia, mas o Bundle e Config UI declaram 7.11.0; isso não autoriza preencher retroativamente a metadata física do Core.

## 8. Riscos
1. interpretar Bundle como segundo sistema de NPC;
2. duplicar conteúdo do Core na documentação/integração;
3. versões da família desalinhadas;
4. dependência ausente;
5. duas cópias do mesmo módulo;
6. voltar a usar jar-in-jar e criar mod ID duplicado;
7. classloading/handshake divergente entre cliente e servidor;
8. remover Core/Config UI porque o Bundle parece 'completo'.

## 9. Matriz de testes
1. Dedicated server boot com os três JARs.
2. Client connect com o mesmo conjunto.
3. Confirmar presença única de cada mod id.
4. Abrir Config UI e editar NPC do Core.
5. Reiniciar servidor e confirmar persistência.
6. Atualizar a família em cópia de teste e verificar dependency resolution.
7. Remover uma dependência em instância descartável e confirmar falha clara, não comportamento parcial silencioso.

**Esta catalogação não afirma que esses testes foram executados.**

## 10. Evidências
- modlist física canônica de 08/09/2026: Bundle 7.11.0 + Core + Config UI como JARs top-level;
- documentação oficial Easy NPC Bundle: pacote de conveniência, módulos separados por dependência e abandono do antigo bundle jar-in-jar.

> **Boundary canônico:** Bundle possui **dependency/distribution semantics**. NPC gameplay pertence ao Core; edição pertence ao Config UI.
