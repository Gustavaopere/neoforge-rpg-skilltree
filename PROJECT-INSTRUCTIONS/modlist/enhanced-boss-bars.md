# Enhanced Boss Bars

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81069b34cdbfdc2b7ca4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Enhanced Boss Bars
- **Arquivo JAR:** `enhancedbossbars-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Camada client-side de HUD que substitui/compatibiliza a apresentação de boss bars para bosses vanilla e suportados por mods, sem alterar vida, IA ou combate.
- **Dependências:** Cliente NeoForge 1.21.1. Providers suportados fisicamente relevantes no pack incluem Mowzie's Mobs 1.8.2, L_Ender's Cataclysm 3.33, Bosses of Mass Destruction 1.3.3 e Alex's Mobs Continued 2.1.11. Jade 15.10.6 também está presente.
- **Sobreposição:** Sobrepõe apenas apresentação de boss bar. Jade e resource packs podem ocupar a mesma região de HUD; os boss mods continuam authority de HP, fases, AI e combate.
- **Compatibilidade/Riscos:** Risco estritamente visual/HUD: boss bar duplicada, deslocada ou sobreposta por Jade/resource packs/outros HUD mods, identificação incorreta de boss ou suporte incompleto a uma build modded. Não altera boss health/AI. A documentação do ecossistema recomenda ajustar Jade quando ele também mostra boss bars.
- **Observações:** Runtime físico confirmado 1.0.0. O mod foi criado para resolver casos em que apenas o resource pack não integra corretamente boss bars de mods. O suporte publicado inclui bosses de Mowzie's Mobs, Cataclysm, Bosses of Mass Destruction, Aether, Meet Your Fight, Wither Storm e Alex's Mobs; somente providers fisicamente presentes são tratados como ativos localmente.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `enhancedbossbars-1.0.0.jar`, mod id `enhancedbossbars`, versão 1.0.0 e SHA-1 812fa35e45f015661e8348c96d2ccc360723583b. Documentação oficial do projeto sustenta o escopo visual e mod support.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Enhanced Boss Bars 1.0.0; client-only HUD/boss support, local boss providers, Jade overlap, lifecycle, risks and tests cataloged.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `enhancedbossbars-1.0.0.jar` · mod id `enhancedbossbars` · versão `1.0.0` · NeoForge 1.21.1 · **client-side**.

## 1. Papel no modpack
Enhanced Boss Bars é uma camada de apresentação para boss bars. O objetivo é mostrar barras estilizadas e compatíveis para bosses vanilla/modded, inclusive casos em que o resource pack sozinho não consegue identificar/renderizar corretamente o boss.

## 2. Authority / ownership
- **Boss provider:** HP, fase, AI, target, damage e existência do boss.
- **Minecraft boss event/state:** valor lógico da barra.
- **Enhanced Boss Bars:** skin/layout/identificação visual suportada.

O mod não pode ser usado como fonte de verdade de vida ou fase do boss.

## 3. Providers relevantes presentes
O pack possui providers publicados como suportados pelo ecossistema do projeto:
- Mowzie's Mobs `1.8.2`;
- L_Ender's Cataclysm `3.33`;
- Bosses of Mass Destruction `1.3.3`;
- Alex's Mobs Continued `2.1.11`.

A lista pública também menciona outros projetos, mas ausência física impede tratá-los como integrações ativas.

## 4. Boss support não é boss logic
Uma barra customizada para Frostmaw, Ignis, Voidworm ou outro boss não implica alteração de entidade. Eventos de fase, invulnerabilidade, shield ou health scaling continuam pertencendo ao mod do boss.

## 5. Relação com resource pack
O projeto nasceu junto de um resource pack de boss bars e o mod existe para corrigir limitações de compatibilidade dessa abordagem em mods que implementam suas barras de maneira própria. Resource pack e mod são camadas visuais; nenhum substitui gameplay.

## 6. Jade
`Jade 15.10.6` está instalado. A documentação do ecossistema Enhanced Boss Bars alerta que a exibição de bosses do Jade pode competir visualmente com boss bars customizadas e orienta desativar/reconfigurar essa superfície quando necessário.

Isso é configuração de HUD, não incompatibilidade de gameplay.

## 7. Client-only boundary
A função é visual. Dedicated server não precisa usar a barra para processar boss state. Dois clientes podem ter apresentação diferente sem alterar HP/AI do mesmo boss.

## 8. Lifecycle
Validar:
- boss spawn/despawn;
- entrada/saída do tracking range;
- fase change;
- death;
- dimension change;
- reconnect;
- resource reload;
- alteração de GUI scale;
- múltiplos bosses simultâneos;
- update do boss provider.

## 9. Multiplayer
Cada cliente renderiza a barra com base no state recebido. Barras não podem criar health state próprio nem atrasar/duplicar damage feedback lógico.

## 10. Riscos
1. boss bar duplicada;
2. Jade ocupar a mesma área;
3. resource pack e mod aplicarem estilos incompatíveis;
4. boss mod atualizar ID/render contract;
5. barra antiga persistir após death/despawn;
6. múltiplos bosses sobreporem HUD;
7. identificação visual errada ser confundida com health bug;
8. suporte publicado a outro MC/mod version ser presumido para a versão local.

## 11. Matriz de testes
1. Cliente NeoForge com o mod ativo e servidor sem dependência lógica nele.
2. Boss vanilla.
3. Mowzie's Mobs: boss suportado.
4. Cataclysm: boss suportado.
5. BOMD: boss suportado.
6. Alex's Mobs: Voidworm quando acessível.
7. Dois bosses simultâneos.
8. Jade ligado/desligado ou reconfigurado.
9. Resource reload e GUI scale diferentes.
10. Reconnect durante fight e death do boss.

**Esta catalogação não afirma que esses testes foram executados.**

## 12. Evidências
- modlist física canônica: JAR/mod id/version/hash e providers locais;
- CurseForge oficial Enhanced Boss Bars / projeto relacionado: finalidade visual, mod support e motivo do mod frente às limitações do resource pack.

> **Boundary canônico:** Enhanced Boss Bars controla somente a **apresentação da boss bar**. O boss provider continua authority de todo o combate.
